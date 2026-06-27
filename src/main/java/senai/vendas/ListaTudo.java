package senai.vendas;

import java.sql.SQLException;

public class ListaTudo {
    public static void main(String[] args) {
        // dados da conexao
        String url = "jdbc:postgresql://ep-twilight-moon-acwrtu3o-pooler.sa-east-1.aws.neon.tech/aulas";
        String usuario = "senaipato";
        String senha = "SenaiPatoBranco";

        try {
            // conecta no banco
            // prepara o statement
            // executa a query
            // imprime os registros
        } catch(SQLException ex) {
            System.out.println("Erro de banco de dados");
        }
    }
}
