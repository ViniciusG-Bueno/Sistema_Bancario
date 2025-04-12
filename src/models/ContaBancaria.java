package models;

import custom.exceptions.SaqueException;

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

    public double sacar(double valor) throws SaqueException{
        if(this.saldo < valor){
            throw new SaqueException();
        }
        this.saldo -= valor;
        return this.saldo;
    }

    public double exibirSaldo(){
        return this.saldo;
    }
}
