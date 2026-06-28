package model.dao;

import model.bean.Utente;
import model.db.DBConnection;
import model.utils.PasswordUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;

public class UtenteDAO {

    public boolean registra(Utente utente) throws SQLException {
        String sql = "INSERT INTO utente " +
                "(nome, cognome, email, password_hash, nickname, telefono, ruolo) " +
                "VALUES (?, ?, ?, ?, ?, ?, 'cliente')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            ps.setString(4, PasswordUtils.cifra(utente.getPasswordHash()));
            ps.setString(5, utente.getNickname());

            if (utente.getTelefono() != null && !utente.getTelefono().trim().isEmpty()) {
                ps.setString(6, utente.getTelefono());
            } else {
                ps.setNull(6, Types.VARCHAR);
            }

            int righe = ps.executeUpdate();
            return righe > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        }
    }

    public Utente login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM utente WHERE email = ? AND password_hash = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, PasswordUtils.cifra(password));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Utente utente = new Utente();
                    utente.setIdUtente(rs.getInt("id_utente"));
                    utente.setNome(rs.getString("nome"));
                    utente.setCognome(rs.getString("cognome"));
                    utente.setEmail(rs.getString("email"));
                    utente.setNickname(rs.getString("nickname"));
                    utente.setTelefono(rs.getString("telefono"));
                    utente.setRuolo(rs.getString("ruolo"));
                    return utente;
                }
            }
        }

        return null;
    }

    public boolean emailEsiste(String email) throws SQLException {
        String sql = "SELECT id_utente FROM utente WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean nicknameEsiste(String nickname) throws SQLException {
        String sql = "SELECT id_utente FROM utente WHERE nickname = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nickname);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public Utente doRetrieveByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM utente WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Utente utente = new Utente();
                    utente.setIdUtente(rs.getInt("id_utente"));
                    utente.setNome(rs.getString("nome"));
                    utente.setCognome(rs.getString("cognome"));
                    utente.setEmail(rs.getString("email"));
                    utente.setNickname(rs.getString("nickname"));
                    utente.setTelefono(rs.getString("telefono"));
                    utente.setRuolo(rs.getString("ruolo"));
                    return utente;
                }
            }
        }

        return null;
    }
}