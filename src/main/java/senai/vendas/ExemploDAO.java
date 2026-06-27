package senai.vendas;

import java.time.LocalDate;

public class ExemploDAO {
    public static void main(String[] args) {
        // cria o objeto de conexão vendas/postgres
        VendasDAO dao = new VendasDAO();
        // criei uma venda
        Venda v = new Venda("Juca da Silva", LocalDate.now(), "Pato Branco", "PR", 1234.56);
        // grava no
        dao.inserir(v);
        System.out.println("Venda criada com id "+v.getId());

        // pega pelo id
        Venda v2 = dao.buscarPorId(v.getId());
        System.out.println("v2 = "+v2);

        // altera
        v2.setValor(2345.67);
        v2.setCidade("Francisco Beltrão");
        dao.atualizar(v2);

        // apaga o registro criado
        dao.deletar(v.getId());
        System.out.println("Venda deletada com id "+v.getId());
    }
}
