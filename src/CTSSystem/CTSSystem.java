package CTSSystem;

import CTSException.CTSException;
import CTSException.ExOther;
import User.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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
    private void QUIT(String[] args){
        isQuit = true;
        System.out.println("----- Good Bye! -----");
    }

    //超级管理员权限转换
    private boolean isSuper = false;
    private void TunakTunakTun(String[] args) throws CTSException {
        if(isSuper == true){
            throw new CTSException(ExOther.permissionSet);
        }
        isSuper = true;
        System.out.println("DuluDulu");
    }
    private void NutKanutKanut(String[] args) throws CTSException{
        if(isSuper == false){
            throw new CTSException(ExOther.permissionSet);
        }
        isSuper = false;
        System.out.println("DaDaDa");
    }

    //用户相关
    private void addUser(String[] args) throws CTSException {
        if(args.length != 3){
            throw new CTSException(ExOther.argumentNum);
        }
        User.addUser(args[0], args[1], args[2]);
    }

    //线路管理
    private void addLine(String[] args){

    }


    public void start(){
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
        try{
            Method method = CTSSystem.class.getDeclaredMethod(words[0],String[].class);
            method.invoke(this , (Object)args);
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            if(e.getCause() instanceof CTSException){
                ((CTSException)(e.getCause())).printException();
            }else{
                System.out.println("反射部分错误");
            }
        }
    }
}
