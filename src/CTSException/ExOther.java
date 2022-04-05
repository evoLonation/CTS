package CTSException;

public enum ExOther implements ExType{
    argumentIllegal("Arguments illegal"),
    permissionSet("WanNiBa");
    String message;
    ExOther(String message){
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}
