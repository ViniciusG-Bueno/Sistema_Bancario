package models;

public class ContaPoupanca extends ContaBancaria implements Tributavel {
    private static double rendimentoMensal;

    public ContaPoupanca(String titular, double saldo, int conta, int agencia, String senha) {
        super(titular, saldo, conta, agencia, senha);
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
        // Método não implementado
        throw new UnsupportedOperationException("Unimplemented method 'calcularIR'");
    }

}
