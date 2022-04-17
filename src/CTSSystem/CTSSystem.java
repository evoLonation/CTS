package CTSSystem;

import CTSException.*;
import Line.*;
import User.*;
import Train.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class CTSSystem {
    //单例类实现
    static private CTSSystem onlyInstance = new CTSSystem();
    static public CTSSystem getInstance(){
        return onlyInstance;
    }
    private CTSSystem(){}

    //系统退出功能实现
    private boolean isQuit = false;
    public void setQuit(){
        isQuit = true;
    }
    //超级管理员权限转换
    private boolean isSuper = false;
    public boolean isSuper() {return isSuper;}
    public void setSuper(boolean isSuper) throws DebugException{
        if(isSuper == this.isSuper)
            throw new DebugException(ExOther.permissionSet);
        this.isSuper = isSuper;
    }

    // 用户登录部分
    private User nowUser;
    public void login(String aadhaar) throws DebugException {
        if(nowUser != null)
            throw new DebugException(ExUser.alreadyLogin);
        nowUser = UserSystem.getInstance().getUserByAadhaar(aadhaar);
    }
    public boolean isLogin() {
        return nowUser != null;
    }
    public void logout() throws DebugException {
        if(nowUser == null)
            throw new DebugException(ExUser.noLoggedIn);
        nowUser = null;
    }
    public User getNowUser() throws DebugException {
        if(nowUser == null)
            throw new DebugException(ExUser.noLoggedIn);
        return nowUser;
    }



    //执行部分
    public void run(){
        Scanner in = new Scanner(System.in);
        while (!isQuit) {
            String command = in.nextLine();
            execute(command);
        }
        in.close();
        return;
    }

    private void execute(String command){
        String[] words = command.split(" +");
        String args[] = Arrays.copyOfRange(words, 1, words.length);
        if(words[0].equals(""))return;
        Class commandClass;
        Command commandInstance;
        try{
            commandClass = Class.forName("CTSSystem." + words[0]);
            commandInstance = (Command)(commandClass.getDeclaredConstructor(String[].class)).newInstance((Object)args);
        }
        catch (Throwable e){
            if(e instanceof ClassNotFoundException || e instanceof NoClassDefFoundError) {
                CTSException argError = new CTSException(ExOther.commandNoExist);
                argError.printException();
            }else{
                e.printStackTrace();
            }
            return;
        }
        try {
            commandInstance.execute();
        }
        catch (CTSException e){
            e.printException();
        }
        return;
    }



}
