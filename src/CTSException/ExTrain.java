package CTSException;

public enum ExTrain implements ExType{
    ticketPrice("Price illegal"),
    ticketNum("Ticket number illegal"),
    lineExistAndFree("Line illegal"),
    trainSerialExist("Train serial duplicate"),
    trainSerialNoExist("Train serial does not exist"),
    trainNoExist("Train does not exist"),
    trainSerial("Train serial illegal"),
    noHaveSeat("Seat does not match"),
    ticketNotEnough("Ticket does not enough"),

    //自定义
    alreadySetLine("already set line"),
    noLineWith("no Line To Relieve"),
    typeNumNoMatch("type number does not match");

    String message;
    ExTrain(String message){
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}
