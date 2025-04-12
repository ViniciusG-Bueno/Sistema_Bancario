package models;

import custom.exceptions.SaqueException;

public class ContaCorrente extends ContaBancaria {

    private static double tarifa; 

    public ContaCorrente(String titular, double saldo, int conta, int agencia, String senha) {
        super(titular, saldo, conta, agencia, senha);
    }

    public double sacar(double valor) throws SaqueException{
        if(this.getSaldo() < valor + tarifa){
            throw new SaqueException();
        }
        this.setSaldo(this.getSaldo() - (valor + tarifa));
        return this.getSaldo();
        }

}
