package senai; // talvez precise mudar isso

import senai.tabela.Estado;
import senai.tabela.Regiao; // e isso

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

                // puxa a lista de regiões pro arraylist
                List<Regiao> brasil = listaRegioes(conexao);

                // agora lista o arraylist
                for (Regiao regiao : brasil) {
                    System.out.printf("Região %s (%s)\n",
                            regiao.getNome(), regiao.getSigla()
                    );
                    for(Estado estado : regiao.getEstados()){
                        System.out.printf("\t%s - %s\n", estado.getSigla(), estado.getNome());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } // fecha a conexão
    }

    private static List<Regiao> listaRegioes(Connection conexao) {

        // fazer um select na tabela regiao
        try (Statement sta = conexao.createStatement();
             ResultSet res = sta.executeQuery("select * from regiao;");) {

            // listar todas as linhas da tabela
            ArrayList<Regiao> brasil = new ArrayList<>();

            // puxa uma linha do resultado da query
            while (res.next()) {
                Regiao regiao = new Regiao();
                int id = res.getInt("id"); // puxa o campo "id" dessa linha como um int
                regiao.setId(id);
                String sigla = res.getString("sigla");
                regiao.setSigla(sigla);
                String nome = res.getString("nome");
                regiao.setNome(nome);

                // guarda na lista
                brasil.add(regiao);

                // agora puxa a lista de estados dessa regiao
                regiao.setEstados( listaEstados(conexao, regiao) );
            }

            // devolve a lista pra main
            return brasil;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // não conseguiu criar a lista :(
    }

    private static List<Estado> listaEstados(Connection conexao, Regiao regiao) {
        try (PreparedStatement sta = conexao.prepareStatement("select * from estado where regiao_id = ? order by nome;")){
            // injeta o id da região na query
            sta.setInt(1, regiao.getId());

            // agora roda a query que eu preparei
            ResultSet res = sta.executeQuery();

            ArrayList<Estado> estados = new ArrayList<>();
            while (res.next()) {
                Estado estado = new Estado();
                estado.setId(res.getInt("id"));
                estado.setNome(res.getString("nome"));
                estado.setSigla(res.getString("sigla"));
                estado.setRegiao(regiao);
                // soca na lista
                estados.add(estado);
            }

            return estados;

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // não conseguiu criar a lista :(
    }

}
