package model.dao;

import model.bean.DettaglioOrdine;
import model.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DettaglioOrdineDAO {

    public List<DettaglioOrdine> getByOrdine(int ordineId) throws SQLException {
        List<DettaglioOrdine> lista = new ArrayList<>();
        String sql = "SELECT * FROM dettaglio_ordine WHERE ordine_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ordineId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
            }
        }

        return lista;
    }

    private DettaglioOrdine mapRow(ResultSet rs) throws SQLException {
        DettaglioOrdine d = new DettaglioOrdine();
        d.setId(rs.getInt("id_dettaglio_ordine"));
        d.setOrdineId(rs.getInt("ordine_id"));
        d.setProdottoId((Integer) rs.getObject("prodotto_id"));
        d.setNomeProdotto(rs.getString("nome_prodotto"));
        d.setPrezzoUnitario(rs.getBigDecimal("prezzo_unitario"));
        d.setIva(rs.getBigDecimal("iva"));
        d.setQuantita(rs.getInt("quantita"));
        return d;
    }
}