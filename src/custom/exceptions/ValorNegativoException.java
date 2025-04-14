package custom.exceptions;

public class ValorNegativoException extends Exception {
    
    public ValorNegativoException(String mensagem) {
        super(mensagem);
    }

    public ValorNegativoException(){
        super("Valor inválido. Valor menor que zero.");
    }
}
