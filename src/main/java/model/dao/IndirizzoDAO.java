package model.dao;

import model.bean.Indirizzo;
import model.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IndirizzoDAO {

    public void inserisci(Indirizzo i) throws SQLException {
        String sql = "INSERT INTO indirizzo (utente_id, via, citta, cap, provincia, tipo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, i.getUtenteId());
            ps.setString(2, i.getVia());
            ps.setString(3, i.getCitta());
            ps.setString(4, i.getCap());
            ps.setString(5, i.getProvincia());
            ps.setString(6, i.getTipo());
            ps.executeUpdate();
        }
    }

    public List<Indirizzo> getByUtente(int utenteId) throws SQLException {
        List<Indirizzo> lista = new ArrayList<>();
        String sql = "SELECT * FROM indirizzo WHERE utente_id = ?";

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

    public Indirizzo getById(int id) throws SQLException {
        String sql = "SELECT * FROM indirizzo WHERE id_indirizzo = ?";

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

    private Indirizzo mapRow(ResultSet rs) throws SQLException {
        Indirizzo i = new Indirizzo();
        i.setId(rs.getInt("id_indirizzo"));
        i.setUtenteId(rs.getInt("utente_id"));
        i.setVia(rs.getString("via"));
        i.setCitta(rs.getString("citta"));
        i.setCap(rs.getString("cap"));
        i.setProvincia(rs.getString("provincia"));
        i.setTipo(rs.getString("tipo"));
        return i;
    }
}