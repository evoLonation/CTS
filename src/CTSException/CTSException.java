package CTSException;

public class CTSException extends java.lang.Exception {
    String message;
    public CTSException(ExType e){
        message = e.toString();
    }
    public void printException(){
        System.out.println(message);
    }
}
