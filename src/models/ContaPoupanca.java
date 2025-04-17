package models;

public class ContaPoupanca extends ContaBancaria implements Tributavel {
    private static double rendimentoMensal = 0.5;

    public ContaPoupanca(int id, String titular, double saldo, int conta, int agencia, String senha) {
        super(id, titular, saldo, conta, agencia, senha);
    }

    public ContaPoupanca(int id){
        super(id);
    }

    public void aplicarRendimentoMensal(){
        double saldoAtual = this.getSaldo();
        double rendimento = saldoAtual * (rendimentoMensal / 100);
        this.setSaldo(saldoAtual + rendimento);
    }

    public static void setRendimentoMensal(double rendimentoMensal) {
        ContaPoupanca.rendimentoMensal = rendimentoMensal;
    }

    @Override
    public double calcularIR() {
        double saldoAtual = this.getSaldo();
        double rendimento = saldoAtual * (rendimentoMensal / 100);
        double imposto = rendimento * 0.15; // 15% sobre o rendimento
        return imposto;
    }

}
