package User;

import CTSException.ExUser;
import CTSException.CTSException;

import java.util.ArrayList;

public class User {
    static ArrayList<User> users = new ArrayList<User>();
    String name;
    String gender;
    String aadhaar;

    private String getName() {
        return name;
    }

    private String getGender() {
        return gender;
    }

    private String getAadhaar() {
        return aadhaar;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setGender(String gender) {
        this.gender = gender;
    }

    private void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    private static boolean isAadhaar(String str) {
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

    //保证合法
    static private String aadhaarGender(String aadhaar){
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

    private static boolean isGender(String str) {
        if (str.matches("[MFO]")) {
            return true;
        }
        return false;
    }

    private static boolean isName(String str) {
        if (str.matches("[a-zA-Z_]+")) {
            return true;
        }
        return false;
    }
    //保证卡号已经合法
    private static boolean aadhaarIsRegister(String aadhaar) {
        for (User u : users) {
            if (u.aadhaar.equals(aadhaar))
                return false;
        }
        return true;
    }

    static public void addUser(String name, String gender, String aadhaar)throws CTSException{
        if (!isName(name)) {
            throw new CTSException(ExUser.name);
        }
        if (!User.isGender(gender)) {
            throw new CTSException(ExUser.gender);
        }
        if (!User.isAadhaar(aadhaar) || !aadhaarGender(aadhaar).equals(gender)) {
            throw new CTSException(ExUser.aadhaarIllegal);
        }
        if (!User.aadhaarIsRegister(aadhaar)) {
            throw new CTSException(ExUser.aadhaarExist);
        }
        User user = new User();
        user.setName(name);
        user.setGender(gender);
        user.setAadhaar(aadhaar);
        users.add(user);
        user.toString();
    }

    @Override
    public String toString() {
        System.out.println("Name:" + name);
        System.out.println("Sex:" + gender);
        System.out.println("Aadhaar:" + aadhaar);
        return "Name:" + name + "\n" + "Sex:" + gender + "\n" + "Aadhaar:" + aadhaar;
    }
}
