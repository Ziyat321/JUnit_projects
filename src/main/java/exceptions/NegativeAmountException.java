package exceptions;

public class NegativeAmountException extends RuntimeException{
    public NegativeAmountException(){
        super("Отрицательное значение.");
    }

    public NegativeAmountException(String message){
        super(message);
    }
}
