package CTSException;

public enum ExLine implements ExType{
    stationExist("Station duplicate"),
    stationNoExist("Station does not exist"),
    capacity("Capacity illegal"),
    lineExist("Line already exists"),
    lineNoExist("Line does not exist");

    String message;
    ExLine(String message){
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}
