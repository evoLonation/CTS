package User;

import CTSException.*;

import java.util.ArrayList;

final public class UserSystem{
    //单例类实现
    static private UserSystem onlyInstance = new UserSystem();
    static public UserSystem getInstance(){
        return onlyInstance;
    }

    private UserSystem() {}
    private ArrayList<User> users = new ArrayList<User> ();

    public void addUser(String name, String gender, String aadhaar)throws CTSException {
        User user = new User(name, gender, aadhaar);
        users.add(user);
        System.out.println(user);
    }
    //保证卡号已经合法
    public boolean aadhaarIsRegister(String aadhaar) {
        for (User u : users) {
            if (u.aadhaar.equals(aadhaar))
                return false;
        }
        return true;
    }
}
