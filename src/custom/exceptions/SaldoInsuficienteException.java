package custom.exceptions;

public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String message) {
        super(message);
    }

    public SaldoInsuficienteException(){
        super("Saldo insuficiente");
    }

}