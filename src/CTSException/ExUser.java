package CTSException;

public enum ExUser implements ExType{
    name("Name illegal"),
    gender("Sex illegal"),
    aadhaarIllegal("Aadhaar number illegal"),
    aadhaarExist("Aadhaar number exist");
    String message;
    ExUser(String message){
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}