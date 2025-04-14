package models;

import custom.exceptions.SaldoInsuficienteException;
import custom.exceptions.ValorNegativoException;

public class ContaCorrente extends ContaBancaria {

    private static double tarifa; 

    public ContaCorrente(String titular, double saldo, int conta, int agencia, String senha) {
        super(titular, saldo, conta, agencia, senha);
    }

    public double sacar(double valor) throws SaldoInsuficienteException, ValorNegativoException{
        if(this.getSaldo() < valor + tarifa){
            throw new SaldoInsuficienteException();
        }

        if (valor <= 0){
            throw new ValorNegativoException("O valor do saque deve ser maior que zero.");
        }
        
        this.setSaldo(this.getSaldo() - (valor + tarifa));
        return this.getSaldo();
        }

}
