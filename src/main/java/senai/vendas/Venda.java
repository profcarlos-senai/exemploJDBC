package senai.vendas;

import java.time.LocalDate;

public class Venda {

    private Integer id;
    private String vendedor;
    private LocalDate dataVenda;
    private String cidade;
    private String uf;
    private Double valor;

    public Venda(){
        // vazio
    }

    public Venda(int id, String vendedor, LocalDate dataVenda, String cidade, String uf, double valor) {
        this.id = id;
        this.vendedor = vendedor;
        this.dataVenda = dataVenda;
        this.cidade = cidade;
        this.uf = uf;
        this.valor = valor;
    }

    public Venda(String vendedor, LocalDate dataVenda, String cidade, String uf, double valor) {
        this.vendedor = vendedor;
        this.dataVenda = dataVenda;
        this.cidade = cidade;
        this.uf = uf;
        this.valor = valor;
    }

    public Integer getId() { return id; }
    public String getVendedor() { return vendedor; }
    public LocalDate getDataVenda() { return dataVenda; }
    public String getCidade() { return cidade; }
    public String getUf() { return uf; }
    public Double getValor() { return valor; }

    public void setId(Integer id) { this.id = id; }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", vendedor='" + vendedor + '\'' +
                ", dataVenda=" + dataVenda +
                ", cidade='" + cidade + '\'' +
                ", uf='" + uf + '\'' +
                ", valor=" + valor +
                '}';
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}