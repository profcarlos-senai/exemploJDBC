package senai; // talvez precise mudar isso

import senai.tabela.Cidade;
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
                        for(Cidade cidade : estado.getCidades()){
                            System.out.println("\t\t" + cidade.getNome()+ "("+cidade.getPopulacao()/1000+" mil hab)");
                        }
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
            e.printStackTrace(); // mostra o erro do java na tela
        }

        return null; // não conseguiu criar a lista :(
    }

    private static List<Estado> listaEstados(Connection conexao, Regiao regiao) {
        try (PreparedStatement sta = conexao.prepareStatement("select * from estado where regiao_id = ? order by nome;")){
            // substitui o 1º "?" da query por um int vindo de regiao.getId()
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

                // pega a lista de cidades
                estado.setCidades( listaCidades(conexao, estado));
            }

            return estados;

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // não conseguiu criar a lista :(
    }

    private static List<Cidade> listaCidades(Connection conexao, Estado estado) {
        String sql = "select * from cidade where uf_id = ? order by nome;";
        try (PreparedStatement sta = conexao.prepareStatement(sql)){
            // substitui o 1º "?" da query por um int vindo de estado.getId()
            sta.setInt(1, estado.getId());

            // agora roda a query que eu preparei
            ResultSet res = sta.executeQuery();

            ArrayList<Cidade> cidades = new ArrayList<>();
            while (res.next()) {
                Cidade cidade = new Cidade();
                cidade.setId( res.getInt("id") );
                cidade.setNome( res.getString("nome") );
                cidade.setPopulacao( res.getInt("populacao") );
                cidade.setDdd( res.getInt("ddd") );
                cidade.setEstado( estado );
                // soca na lista
                cidades.add(cidade);
            }

            return cidades;

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // não conseguiu criar a lista :(
    }

}
