package models;

import custom.exceptions.SaldoInsuficienteException;
import custom.exceptions.ValorNegativoException;

public abstract class ContaBancaria {
    private String titular;
    private double saldo;
    private int conta;
    private int agencia;
    private String senha;

    public ContaBancaria(String titular, double saldo, int conta, int agencia, String senha) {
        this.titular = titular;
        this.saldo = saldo;
        this.conta = conta;
        this.agencia = agencia;
        this.senha = senha;
    }

    public double getSaldo(){
        return this.saldo;
    }

    public void setSaldo(double saldo){
        this.saldo = saldo;
    }

    public double depositar(double valor){
        this.saldo += valor;
        return this.saldo;
    }

    public double sacar(double valor) throws SaldoInsuficienteException, ValorNegativoException{
        if(this.saldo < valor){
            throw new SaldoInsuficienteException();
        }

        if (valor <= 0){
            throw new ValorNegativoException("O valor do saque deve ser maior que zero.");
        }

        this.saldo -= valor;
        return this.saldo;


    }

    public double exibirSaldo(){
        return this.saldo;
    }
}
