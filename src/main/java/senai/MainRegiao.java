package senai; // talvez precise mudar isso

import senai.tabela.Regiao; // e isso

import java.sql.*;

public class MainRegiao {
    public static void main(String[] args) {
        // conectar no banco
        String url = "jdbc:postgresql://ep-twilight-moon-acwrtu3o-pooler.sa-east-1.aws.neon.tech/aulas";
        String usuario = "senaipato";
        String senha = "SenaiPatoBranco";

        // Tentativa de conexão
        try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
            if (conexao != null) {
                System.out.println("Conexão estabelecida com sucesso!");

                // fazer um select na tabela regiao
                Statement sta = conexao.createStatement();
                ResultSet res = sta.executeQuery("select * from regiao;");

                // listar todas as linhas da tabela
                while (res.next()) {
                    Regiao regiao = new Regiao();
                    regiao.setId( res.getInt("id") );
                    regiao.setSigla( res.getString("sigla") );
                    regiao.setNome( res.getString("nome") );

                    System.out.printf("Região %s (%s) - cód. %d\n",
                            regiao.getNome(), regiao.getSigla(), regiao.getId()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } // fecha a conexão
    }
}
