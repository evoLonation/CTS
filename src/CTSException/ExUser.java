package CTSException;

public enum ExUser implements ExType{
    name("Name illegal"),
    gender("Sex illegal"),
    aadhaarIllegal("Aadhaar number illegal"),
    aadhaarExist("Aadhaar number exist"),
    alreadyLogin("You have logged in"),
    userNotExist("User does not exist"),
    wrongName("Wrong name"),
    noLoggedIn("No user has logged in");
    String message;
    ExUser(String message){
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}