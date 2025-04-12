package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Empresa {
    private String nome;
    private String cnpj;
    private List<String> emails;
    private List<Funcionario> funcionarios = new ArrayList<>();
    private HashMap<String, Produto> produtos = new HashMap<>();


    public Empresa(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.emails = new ArrayList<>();
    }

    public void cadastrarFuncionario(Funcionario funcionario){
        this.funcionarios.add(funcionario);
    }

    public void removerFuncionario(Funcionario funcionario){
        this.funcionarios.remove(funcionario);
    }

    public void exibirFuncionarios(){
        for(Funcionario funcionario : this.funcionarios){
            funcionario.exibirInformacoes();
        }
    }

    public boolean cadastrarEmail(String email){
        if(emails.size() >= 5){
            System.out.println("Limite de 5 e-mails atingido!");
            return false;
        }
        return emails.add(email);
    }

    public boolean removerEmail(String email){
        return emails.remove(email);
    }

    public void exibirEmails(){
        for(String email : this.emails){
            System.out.println("- " + email);
        }
    }

    public void cadastrarProduto(Produto produto){
        this.produtos.put(produto.getNome(), produto);
    }

    public void removerProduto(Produto produto){
        produtos.remove(produto.getNome());
    }

    public void exibirProdutos(){
        produtos.values().stream()
        .forEach(produto -> produto.exibirInformacoes());
    }

    // public void buscarProdutoPorNome(String nome, int codigo, double valor){
    //     produtos.values().stream()
    //     .filter(produto -> produto.getNome().equals(nome) && produto.getCodigo() == codigo && produto.getValor() == valor)
    //     .forEach(produto -> produto.exibirInformacoes());
    // }

    public void buscarProdutoPorNome(String nome){
        produtos.values().stream()
        .filter(produto -> produto.getNome().equals(nome))
        .forEach(produto -> produto.exibirInformacoes());
    }

    public void buscarProdutoPorCodigo(int codigo){
        produtos.values().stream()
        .filter(produto -> produto.getCodigo() == codigo)
        .forEach(produto -> produto.exibirInformacoes());
    }

    public void buscarProdutoPorValor(double valor){
        produtos.values().stream()
        .filter(produto -> produto.getValor() == valor)
        .forEach(produto -> produto.exibirInformacoes());
    }

    public void pagarFuncionarios(){
        for(Funcionario funcionario : this.funcionarios){
            funcionario.getContaSalario().depositar(funcionario.getSalario());
        }
    }

}
