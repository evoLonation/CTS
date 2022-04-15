package User;

import CTSException.*;


public class User {
    String name;
    String gender;
    String aadhaar;

    public User(String name, String gender, String aadhaar) throws CTSException {
        try {
            setName(name);
            setGender(gender);
            setAadhaar(aadhaar);
        }
        catch (CTSException e){
            e.printException();
            throw new DebugException();
        }
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public boolean setName(String name) throws CTSException{
        if(!isName(name)) throw new CTSException(ExUser.name);
        this.name = name;
        return true;
    }

    public boolean setGender(String gender) throws CTSException{
        if(!isGender(gender)) throw new CTSException(ExUser.gender);
        this.gender = gender;
        return true;
    }

    public boolean setAadhaar(String aadhaar) throws CTSException{
        if(!isAadhaar(aadhaar)) throw new CTSException(ExUser.aadhaarIllegal);
        this.aadhaar = aadhaar;
        return true;
    }

    static public boolean isAadhaar(String str) {
        // 先保证12位，全数字
        if (!str.matches("\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d")) {
            return false;
        }
        int num1 = Integer.parseInt(str.substring(0, 4));
        int num2 = Integer.parseInt(str.substring(4, 8));
        int num3 = Integer.parseInt(str.substring(8, 12));
        if (num1 < 1 || num1 > 1237)
            return false;
        if (num2 < 20 || num2 > 460)
            return false;
        if (num3 / 10 > 100 || num3 % 10 > 2)
            return false;
        return true;
    }

    static public String aadhaarToGender(String aadhaar) throws CTSException{
        if(!isAadhaar(aadhaar)) throw new CTSException(ExUser.aadhaarIllegal);
        int num = (aadhaar.toCharArray()[11])-'0';
        String ret;
        switch (num){
            case 0:
                ret = "F";
                break;
            case 1:
                ret = "M";
                break;
            case 2:
                ret = "O";
                break;
            default:
                ret = "妈了个巴子";
        }
        return ret;
    }

    static public boolean isGender(String str) {
        if (str.matches("[MFO]")) {
            return true;
        }
        return false;
    }

    static public boolean isName(String str) {
        if (str.matches("[a-zA-Z_]+")) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Name:" + name + "\n" + "Sex:" + gender + "\n" + "Aadhaar:" + aadhaar;
    }
}
