package models;

public class Produto {
    private int codigo;
    private double valor;
    private String nome;
    private int quantidade;

    public Produto(int codigo, double valor, String nome, int quantidade) {
        this.codigo = codigo;
        this.valor = valor;
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public String getNome(){
        return this.nome;
    }

    public int getCodigo(){
        return this.codigo;
    }

    public double getValor(){
        return this.valor;
    }

    public int getQuantidade(){
        return this.quantidade;
    }

  public void exibirInformacoes(){
        System.out.println("Nome: " + this.getNome());
        System.out.println("Codigo: " + this.getCodigo());
        System.out.println("Valor: " + this.getValor());
        System.out.println("Quantidade: " + this.getQuantidade());
    }
}
