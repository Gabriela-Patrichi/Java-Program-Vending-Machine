package exception;

public class NoInventoryException extends Exception{

    @Override // to override the getMessage() Exception class method, to be customised to this exception
    public String getMessage(){
        return "Sorry, item not in stock!";
    }
}
