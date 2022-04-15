package CTSException;

public class DebugException extends CTSException {
    String message = "";
    public DebugException() {}
    public DebugException(ExType e){
        message = e.toString();
    }
    public void printException(){
        System.out.println("This is a debug Exception: " + message);
        printStackTrace();
    }
}
