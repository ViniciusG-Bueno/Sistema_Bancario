package models;

public class Funcionario extends Pessoa implements Tributavel {
    private String cargo;
    private double salario;
    private ContaBancaria contaSalario;

    public Funcionario(String nome, int idade, String endereco, String cpf, String cargo, double salario, ContaBancaria contaSalario) {
        super(nome, idade, endereco, cpf);
        this.cargo = cargo;
        this.salario = salario;
        this.contaSalario = contaSalario;
    }

    public double getSalario(){
        System.out.println("Salario: " + this.salario); 
        return this.salario;
    }

    public double getSalario(double bonus){
        System.out.println("Salario com bonus: " + (this.salario + bonus)); 
        return this.salario + bonus;
    }

    public ContaBancaria getContaSalario(){
        return this.contaSalario;
    }

    @Override
    public double calcularIR() {
        // Método não implementado
        throw new UnsupportedOperationException("Unimplemented method 'calcularIR'");
    }

}
