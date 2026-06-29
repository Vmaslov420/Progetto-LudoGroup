package model.dao;

import model.bean.Prodotto;
import model.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * DAO = Data Access Object.
 *
 * Questo oggetto si occupa solo di accedere al database.
 * Non contiene logica di interfaccia e non genera HTML.
 *
 * Perché usare un DAO?
 * - separa la logica di persistenza dalla logica delle servlet;
 * - rende il codice più ordinato e più facile da mantenere;
 * - rispetta meglio il pattern MVC richiesto dal progetto.
 */
public class ProdottoDAO {

    /*
     * Restituisce tutti i prodotti VISIBILI lato utente.
     *
     * Perché filtriamo con eliminato = FALSE?
     * Perché un prodotto cancellato logicamente non deve comparire
     * nel catalogo pubblico.
     */
    public List<Prodotto> getTutti() throws SQLException {
        List<Prodotto> lista = new ArrayList<>();

        /*
         * LEFT JOIN:
         * ci permette di recuperare anche il nome della categoria.
         *
         * Perché LEFT JOIN e non INNER JOIN?
         * Perché categoria_id può essere NULL.
         * Con LEFT JOIN il prodotto viene comunque restituito anche se non ha categoria.
         */
        String sql = "SELECT p.*, c.nome_categoria AS nome_categoria " +
                "FROM prodotto p " +
                "LEFT JOIN categoria c ON p.categoria_id = c.id_categoria " +
                "WHERE p.eliminato = FALSE";

        /*
         * try-with-resources:
         * Connection, PreparedStatement e ResultSet vengono chiusi automaticamente.
         *
         * Questo evita memory leak e connessioni lasciate aperte.
         * È la sintassi moderna consigliata per lavorare con JDBC.
         */
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            /*
             * rs.next() sposta il cursore alla riga successiva.
             * Finché ci sono righe disponibili, costruiamo un Prodotto
             * e lo aggiungiamo alla lista.
             */
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }

        return lista;
    }

    /*
     * Restituisce tutti i prodotti lato admin.
     *
     * Qui NON filtriamo eliminato = FALSE perché l'amministratore
     * può aver bisogno di vedere anche i prodotti cancellati logicamente.
     */
    public List<Prodotto> getTuttiAdmin() throws SQLException {
        List<Prodotto> lista = new ArrayList<>();

        String sql = "SELECT p.*, c.nome_categoria AS nome_categoria " +
                "FROM prodotto p " +
                "LEFT JOIN categoria c ON p.categoria_id = c.id_categoria";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }

        return lista;
    }

    /*
     * Recupera un singolo prodotto per ID lato utente.
     *
     * Se il prodotto è eliminato logicamente, non deve essere visibile
     * nel catalogo pubblico, quindi lo escludiamo con eliminato = FALSE.
     */
    public Prodotto getById(int id) throws SQLException {
        String sql = "SELECT p.*, c.nome_categoria AS nome_categoria " +
                "FROM prodotto p " +
                "LEFT JOIN categoria c ON p.categoria_id = c.id_categoria " +
                "WHERE p.id_prodotto = ? AND p.eliminato = FALSE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            /*
             * PreparedStatement usa segnaposto "?".
             *
             * Perché si usa questa sintassi?
             * - evita SQL injection;
             * - separa query e valori;
             * - fa gestire al driver JDBC il corretto escaping dei parametri.
             */
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    /*
     * Recupera un prodotto per ID lato admin.
     *
     * Questo metodo è utile, ad esempio, quando l'amministratore
     * vuole modificare un prodotto anche se è stato eliminato logicamente.
     */
    public Prodotto getByIdAdmin(int id) throws SQLException {
        String sql = "SELECT p.*, c.nome_categoria AS nome_categoria " +
                "FROM prodotto p " +
                "LEFT JOIN categoria c ON p.categoria_id = c.id_categoria " +
                "WHERE p.id_prodotto = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    /*
     * Cerca prodotti per nome.
     *
     * Usiamo LIKE per una ricerca testuale semplice.
     * È una buona base sia per catalogo sia per barra di ricerca.
     */
    public List<Prodotto> cerca(String query) throws SQLException {
        List<Prodotto> lista = new ArrayList<>();

        String sql = "SELECT p.*, c.nome_categoria AS nome_categoria " +
                "FROM prodotto p " +
                "LEFT JOIN categoria c ON p.categoria_id = c.id_categoria " +
                "WHERE p.eliminato = FALSE AND p.nome_prodotto LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            /*
             * Mettiamo i wildcard % prima e dopo la query
             * per cercare il testo in qualsiasi posizione del nome.
             */
            ps.setString(1, "%" + query + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
            }
        }

        return lista;
    }

    /*
     * Inserisce un nuovo prodotto nel database.
     */
    public void inserisci(Prodotto p) throws SQLException {
        String sql = "INSERT INTO prodotto " +
                "(nome_prodotto, descrizione_prodotto, prezzo_prodotto, iva, stock, immagine, categoria_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescrizione());
            ps.setBigDecimal(3, p.getPrezzo());
            ps.setBigDecimal(4, p.getIva());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getImmagine());

            /*
             * categoria_id nel database può essere NULL.
             *
             * Se il prodotto ha una categoria, usiamo setInt().
             * Se non ce l'ha, dobbiamo usare setNull(...).
             *
             * Perché non possiamo usare sempre setInt?
             * Perché setInt richiede un valore numerico reale,
             * mentre SQL NULL rappresenta assenza di valore.
             */
            if (p.getCategoriaId() != null) {
                ps.setInt(7, p.getCategoriaId());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            ps.executeUpdate();
        }
    }

    /*
     * Aggiorna i dati di un prodotto esistente.
     */
    public void modifica(Prodotto p) throws SQLException {
        String sql = "UPDATE prodotto SET nome_prodotto = ?, descrizione_prodotto = ?, " +
                "prezzo_prodotto = ?, iva = ?, stock = ?, immagine = ?, categoria_id = ? " +
                "WHERE id_prodotto = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescrizione());
            ps.setBigDecimal(3, p.getPrezzo());
            ps.setBigDecimal(4, p.getIva());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getImmagine());

            if (p.getCategoriaId() != null) {
                ps.setInt(7, p.getCategoriaId());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            ps.setInt(8, p.getId());

            ps.executeUpdate();
        }
    }

    /*
     * Cancellazione logica del prodotto.
     *
     * Non eliminiamo fisicamente la riga dal database,
     * ma impostiamo eliminato = TRUE.
     *
     * Perché?
     * - è più sicuro per la storia degli ordini;
     * - evita problemi di integrità referenziale;
     * - permette all'admin di mantenere traccia dei prodotti.
     */
    public void elimina(int id) throws SQLException {
        String sql = "UPDATE prodotto SET eliminato = TRUE WHERE id_prodotto = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /*
     * Metodo privato di utilità.
     *
     * Serve a trasformare una riga del ResultSet in un oggetto Prodotto.
     * In questo modo evitiamo di ripetere lo stesso codice
     * in tutti i metodi del DAO.
     */
    private Prodotto mapRow(ResultSet rs) throws SQLException {
        Prodotto p = new Prodotto();

        p.setId(rs.getInt("id_prodotto"));
        p.setNome(rs.getString("nome_prodotto"));
        p.setDescrizione(rs.getString("descrizione_prodotto"));
        p.setPrezzo(rs.getBigDecimal("prezzo_prodotto"));
        p.setIva(rs.getBigDecimal("iva"));
        p.setStock(rs.getInt("stock"));
        p.setImmagine(rs.getString("immagine"));

        /*
         * getInt() su una colonna SQL NULL restituisce 0.
         * Questo è un problema se 0 non significa davvero "nessuna categoria".
         *
         * Per questo leggiamo prima l'int,
         * poi controlliamo subito rs.wasNull().
         *
         * wasNull() verifica se l'ULTIMO valore letto dal ResultSet era SQL NULL.
         * È importante chiamarlo subito dopo getInt("categoria_id").
         */
        int categoriaId = rs.getInt("categoria_id");
        if (rs.wasNull()) {
            p.setCategoriaId(null);
        } else {
            p.setCategoriaId(categoriaId);
        }

        p.setNomeCategoria(rs.getString("nome_categoria"));
        p.setEliminato(rs.getBoolean("eliminato"));

        return p;
    }
}