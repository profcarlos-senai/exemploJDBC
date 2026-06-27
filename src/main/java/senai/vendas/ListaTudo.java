package senai.vendas;

import java.sql.*;

public class ListaTudo {
    public static void main(String[] args) {
        // dados da conexao
        String url = "jdbc:postgresql://ep-twilight-moon-acwrtu3o-pooler.sa-east-1.aws.neon.tech/aulas";
        String usuario = "senaipato";
        String senha = "SenaiPatoBranco";

        try {
            // conecta no banco
            Connection con = DriverManager.getConnection(url, usuario, senha);
            // prepara o statement
            String sql = "select * from vendas where vendedor = ? and valor between ? and ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Carlos Silva"); // valor do 1º "?" é "Carlos Silva"
            ps.setDouble(2, 1000.0); // valor do 2º "?" é um int=1000
            ps.setDouble(3, 3000.0); // valor do 3º "?" é um int=3000
            // executa a query
            ResultSet rs = ps.executeQuery();
            // imprime os registros
            while (rs.next()) {
                String vendedor = rs.getString("vendedor");
                Date data = rs.getDate("data_venda");
                double valor = rs.getDouble("valor");
                System.out.println(vendedor+" "+data+" "+valor);
            }
        } catch(SQLException ex) {
                System.out.println("Erro de banco de dados");
        }

    }
}
