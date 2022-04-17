package CTSException;

public enum ExOther implements ExType{
    argumentIllegal("Arguments illegal"),
    other("Unknown error"),
    commandNoExist("Command does not exist"),
    permissionSet("WanNiBa"),
    pleaseLoginFirst("Please login first"),

    //自定义
    objectIsNull("the object is null");
    String message;
    ExOther(String message){
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}
