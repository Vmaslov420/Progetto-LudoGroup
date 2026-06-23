package model.dao;

import model.bean.DettaglioOrdine;
import model.bean.Ordine;
import model.db.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {

    public int inserisci(Ordine o) throws SQLException {
        String sql = "INSERT INTO ordine (utente_id, data_ordine, totale_ordine, stato_ordine, metodo_pagamento, id_indirizzo_spedizione, id_indirirzzo_fatturazione) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, o.getUtenteId());
            ps.setTimestamp(2, o.getData());
            ps.setBigDecimal(3, o.getTotale());
            ps.setString(4, o.getStato());
            ps.setString(5, o.getMetodoPagamento());

            ps.setObject(6, null);
            ps.setObject(7, null);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }

    public void inserisciDettaglio(DettaglioOrdine d) throws SQLException {
        String sql = "INSERT INTO dettaglio_ordine (ordine_id, prodotto_id, nome_prodotto, prezzo_unitario, iva, quantita) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, d.getOrdineId());
            ps.setObject(2, d.getProdottoId());
            ps.setString(3, d.getNomeProdotto());
            ps.setBigDecimal(4, d.getPrezzoUnitario());
            ps.setBigDecimal(5, d.getIva());
            ps.setInt(6, d.getQuantita());
            ps.executeUpdate();
        }
    }

    public List<Ordine> getByUtente(int utenteId) throws SQLException {
        List<Ordine> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordine WHERE utente_id = ? ORDER BY data_ordine DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, utenteId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
            }
        }

        return lista;
    }

    public List<Ordine> getTutti() throws SQLException {
        List<Ordine> lista = new ArrayList<>();
        String sql = "SELECT o.*, CONCAT(u.nome, ' ', u.cognome) AS nome_utente " +
                "FROM ordine o JOIN utente u ON o.utente_id = u.id_utente " +
                "ORDER BY o.data_ordine DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ordine o = mapRow(rs);
                o.setNomeUtente(rs.getString("nome_utente"));
                lista.add(o);
            }
        }

        return lista;
    }

    public List<Ordine> getByDateRange(Date dal, Date al) throws SQLException {
        List<Ordine> lista = new ArrayList<>();
        String sql = "SELECT o.*, CONCAT(u.nome, ' ', u.cognome) AS nome_utente " +
                "FROM ordine o JOIN utente u ON o.utente_id = u.id_utente " +
                "WHERE DATE(o.data_ordine) BETWEEN ? AND ? " +
                "ORDER BY o.data_ordine DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, dal);
            ps.setDate(2, al);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ordine o = mapRow(rs);
                    o.setNomeUtente(rs.getString("nome_utente"));
                    lista.add(o);
                }
            }
        }

        return lista;
    }

    public List<Ordine> getByCliente(String nomeCliente) throws SQLException {
        List<Ordine> lista = new ArrayList<>();
        String sql = "SELECT o.*, CONCAT(u.nome, ' ', u.cognome) AS nome_utente " +
                "FROM ordine o JOIN utente u ON o.utente_id = u.id_utente " +
                "WHERE CONCAT(u.nome, ' ', u.cognome) LIKE ? " +
                "ORDER BY o.data_ordine DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nomeCliente + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ordine o = mapRow(rs);
                    o.setNomeUtente(rs.getString("nome_utente"));
                    lista.add(o);
                }
            }
        }

        return lista;
    }

    private Ordine mapRow(ResultSet rs) throws SQLException {
        Ordine o = new Ordine();
        o.setId(rs.getInt("id_ordine"));
        o.setUtenteId(rs.getInt("utente_id"));
        o.setData(rs.getTimestamp("data_ordine"));
        o.setTotale(rs.getBigDecimal("totale_ordine"));
        o.setStato(rs.getString("stato_ordine"));
        o.setMetodoPagamento(rs.getString("metodo_pagamento"));
        return o;
    }
}