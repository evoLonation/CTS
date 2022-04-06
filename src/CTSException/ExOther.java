package CTSException;

public enum ExOther implements ExType{
    argumentIllegal("Arguments illegal"),
    other("Unknown error"),
    commandNoExist("Command does not exist"),
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
