package custom.exceptions;

public class SaqueException extends Exception {
    public SaqueException(String message) {
        super(message);
    }

    public SaqueException(){
        super("Saldo insuficiente");
    }

    public SaqueException(double valor){
        super("Valor invalido");
    }
}