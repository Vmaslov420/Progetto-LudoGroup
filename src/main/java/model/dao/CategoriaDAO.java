package model.dao;

import model.bean.Categoria;
import model.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
Dao della tabella categoria, l'oggetto legge il catalogo dal database
e le restituisce come lista di bean categoria
 */

public class CategoriaDAO {

    public List<Categoria> getTutte() throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id_categoria, nome_categoria, descrizione_categoria FROM categoria ORDER BY nome_categoria ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id_categoria"));
                categoria.setNome(rs.getString("nome_categoria"));
                categoria.setDescrizione(rs.getString("descrizione_categoria"));
                lista.add(categoria);
            }
        }
        return lista;
    }
}