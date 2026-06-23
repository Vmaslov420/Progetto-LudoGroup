package model.dao;

import model.bean.Prodotto;
import model.db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

    public List<Prodotto> getTutti() throws SQLException {
        List<Prodotto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nome_categoria AS nome_categoria " +
                "FROM prodotto p " +
                "LEFT JOIN categoria c ON p.categoria_id = c.id_categoria " +
                "WHERE p.eliminato = FALSE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        }
        return lista;
    }

    public List<Prodotto> getTuttiAdmin() throws SQLException {
        List<Prodotto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nome_categoria AS nome_categoria " +
                "FROM prodotto p " +
                "LEFT JOIN categoria c ON p.categoria_id = c.id_categoria";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        }
        return lista;
    }

    public Prodotto getById(int id) throws SQLException {
        String sql = "SELECT p.*, c.nome_categoria AS nome_categoria " +
                "FROM prodotto p " +
                "LEFT JOIN categoria c ON p.categoria_id = c.id_categoria " +
                "WHERE p.id_prodotto = ? AND p.eliminato = FALSE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public List<Prodotto> cerca(String query) throws SQLException {
        List<Prodotto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nome_categoria AS nome_categoria " +
                "FROM prodotto p " +
                "LEFT JOIN categoria c ON p.categoria_id = c.id_categoria " +
                "WHERE p.eliminato = FALSE AND p.nome_prodotto LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + query + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    public void inserisci(Prodotto p) throws SQLException {
        String sql = "INSERT INTO prodotto (nome_prodotto, descrizione_prodotto, prezzo_prodotto, iva, stock, immagine, categoria_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescrizione());
            ps.setBigDecimal(3, p.getPrezzo());
            ps.setBigDecimal(4, p.getIva());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getImmagine());
            ps.setInt(7, p.getCategoriaId());
            ps.executeUpdate();
        }
    }

    public void modifica(Prodotto p) throws SQLException {
        String sql = "UPDATE prodotto SET nome_prodotto=?, descrizione_prodotto=?, " +
                "prezzo_prodotto=?, iva=?, stock=?, immagine=?, categoria_id=? " +
                "WHERE id_prodotto=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescrizione());
            ps.setBigDecimal(3, p.getPrezzo());
            ps.setBigDecimal(4, p.getIva());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getImmagine());
            ps.setInt(7, p.getCategoriaId());
            ps.setInt(8, p.getId());
            ps.executeUpdate();
        }
    }

    public void elimina(int id) throws SQLException {
        String sql = "UPDATE prodotto SET eliminato = TRUE WHERE id_prodotto = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Prodotto mapRow(ResultSet rs) throws SQLException {
        Prodotto p = new Prodotto();
        p.setId(rs.getInt("id_prodotto"));
        p.setNome(rs.getString("nome_prodotto"));
        p.setDescrizione(rs.getString("descrizione_prodotto"));
        p.setPrezzo(rs.getBigDecimal("prezzo_prodotto"));
        p.setIva(rs.getBigDecimal("iva"));
        p.setStock(rs.getInt("stock"));
        p.setImmagine(rs.getString("immagine"));
        p.setCategoriaId(rs.getInt("categoria_id"));
        p.setNomeCategoria(rs.getString("nome_categoria"));
        p.setEliminato(rs.getBoolean("eliminato"));
        return p;
    }
}