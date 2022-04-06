package CTSException;

public enum ExTrain implements ExType{
    ticketPrice("Price illegal"),
    ticketNum("Ticket num illegal"),
    lineExistAndFree("Line illegal"),
    trainIdExist("Train serial duplicate"),
    trainIdNoExist("Train serial does not exist"),
    trainNoExist("Train does not exist"),
    trainId("Train serial illegal"),
    noHaveSeat("Seat does not match"),

    //自定义
    alreadySetLine("already set line"),
    noLineWith("no Line To Relieve");

    String message;
    ExTrain(String message){
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}
