package User;

import CTSException.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

final public class UserSystem{
    //单例类实现
    static private UserSystem onlyInstance = new UserSystem();
    static public UserSystem getInstance(){
        return onlyInstance;
    }
    private UserSystem() {}

    private ArrayList<User> users = new ArrayList<User> ();
    private HashMap<String, User> userSet = new HashMap<String, User>();

    public void addUser(User user)throws DebugException {
        if(aadhaarIsRegister(user.getAadhaar()))
            throw new  DebugException(ExUser.aadhaarExist);
        userSet.put(user.getAadhaar(), user);
    }
    public User getUserByAadhaar(String aadhaar) throws DebugException {
        if(!User.isAadhaar(aadhaar))
            throw new DebugException(ExUser.aadhaarIllegal);
        return userSet.get(aadhaar);
    }
    //保证卡号已经合法
    public boolean aadhaarIsRegister(String aadhaar) {
        return userSet.get(aadhaar) != null;
    }
}
