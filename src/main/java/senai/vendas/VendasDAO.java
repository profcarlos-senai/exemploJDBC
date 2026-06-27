package senai.vendas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendasDAO {

    private final String url =  "jdbc:postgresql://ep-twilight-moon-acwrtu3o-pooler.sa-east-1.aws.neon.tech/aulas";
    private final String user = "senaipato";
    private final String password = "SenaiPatoBranco";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // =========================
    // CREATE
    // =========================
    public void inserir(Venda v) {

        String sql = """
            INSERT INTO vendas (vendedor, data_venda, cidade, uf, valor)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getVendedor());
            ps.setDate(2, Date.valueOf(v.getDataVenda()));
            ps.setString(3, v.getCidade());
            ps.setString(4, v.getUf());
            ps.setDouble(5, v.getValor());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idGerado = rs.getInt("id");
                v.setId(idGerado); // injeta o ID no objeto
            }


        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir venda: " + e.getMessage(), e);
        }
    }

    // =========================
    // READ (LISTAR TODOS)
    // =========================
    public List<Venda> listarTodos() {

        List<Venda> lista = new ArrayList<>();

        String sql = """
            SELECT id, vendedor, data_venda, cidade, uf, valor
            FROM vendas
            ORDER BY id
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Venda v = new Venda(
                        rs.getInt("id"),
                        rs.getString("vendedor"),
                        rs.getDate("data_venda").toLocalDate(),
                        rs.getString("cidade"),
                        rs.getString("uf"),
                        rs.getDouble("valor")
                );

                lista.add(v);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendas: " + e.getMessage(), e);
        }

        return lista;
    }

    // =========================
    // READ (POR ID)
    // =========================
    public Venda buscarPorId(int id) {

        String sql = """
            SELECT id, vendedor, data_venda, cidade, uf, valor
            FROM vendas
            WHERE id = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Venda(
                        rs.getInt("id"),
                        rs.getString("vendedor"),
                        rs.getDate("data_venda").toLocalDate(),
                        rs.getString("cidade"),
                        rs.getString("uf"),
                        rs.getDouble("valor")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar venda: " + e.getMessage(), e);
        }

        return null;
    }

    // =========================
    // UPDATE
    // =========================
    public void atualizar(Venda v) {

        String sql = """
            UPDATE vendas
            SET vendedor = ?,
                data_venda = ?,
                cidade = ?,
                uf = ?,
                valor = ?
            WHERE id = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getVendedor());
            ps.setDate(2, Date.valueOf(v.getDataVenda()));
            ps.setString(3, v.getCidade());
            ps.setString(4, v.getUf());
            ps.setDouble(5, v.getValor());
            ps.setInt(6, v.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar venda: " + e.getMessage(), e);
        }
    }

    // =========================
    // DELETE
    // =========================
    public void deletar(int id) {

        String sql = """
            DELETE FROM vendas
            WHERE id = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar venda: " + e.getMessage(), e);
        }
    }
}