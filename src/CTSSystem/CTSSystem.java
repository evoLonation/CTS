package CTSSystem;

import CTSException.*;
import Line.*;
import User.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/*
约定：
1.保证所有的命令函数接收到的args非null

 */
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
            throw new CTSException(ExOther.argumentIllegal);
        }
        User.addUser(args[0], args[1], args[2]);
    }

    //线路管理
    private void addLine(String[] args) throws CTSException{
        if(!isSuper) return;
        if(args.length % 2 == 1){
            throw new CTSException(ExOther.argumentIllegal);
        }
        int capacity;
        try{
            capacity = Integer.parseInt(args[1]);
        }
        catch (Exception e){
            throw new CTSException(ExLine.capacity);
        }
        Line newLine = new Line(args[0], capacity);
        for(int i = 2; i < args.length; i += 2){
            String stationName = args[i];
            int stationDistance;
            try{
                stationDistance = Integer.parseInt(args[i + 1]);
            }
            catch (Exception e){
                throw new CTSException(ExOther.argumentIllegal);
            }
            Station station = new Station(stationName, stationDistance);
            newLine.addStation(station);
        }
        Line.addLine(newLine);
    }
    private void delLine(String[] args) throws CTSException{
        if(!isSuper) return;
        if(args.length != 1) throw new CTSException(ExOther.argumentIllegal);
        Line.deleteLine(args[0]);
    }
    private void lineInfo(String[] args) throws CTSException {
        if(args.length != 1) throw new CTSException(ExOther.argumentIllegal);
        System.out.println(Line.getLineById(args[0]));
    }
    private void listLine(String[] args) throws CTSException {
        if(args.length != 0) throw new CTSException(ExOther.argumentIllegal);
        Collection<Line> lines = Line.getAllLine();
        int i = 1;
        for(Line line : lines){
            System.out.println("[" + Integer.toString(i) + "] " + line);
        }
    }

    // 站点管理
    private void addStation(String[] args) throws CTSException{
        if(!isSuper) return;
        if(args.length != 3) throw new CTSException(ExOther.argumentIllegal);
        Line line = Line.getLineById(args[0]);
        int stationDistance;
        try{
            stationDistance = Integer.parseInt(args[2]);
        }
        catch (Exception e){
            throw new CTSException(ExOther.argumentIllegal);
        }
        Station newStation = new Station(args[1], stationDistance);
        line.addStation(newStation);
    }
    private void delStation(String[] args) throws CTSException{
        if(!isSuper) return;
        if(args.length != 2) throw new CTSException(ExOther.argumentIllegal);
        Line line = Line.getLineById(args[0]);
        line.deleteStation(args[1]);
    }


    //执行部分
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
            method.invoke(this, (Object)args);
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            if(e.getCause() instanceof CTSException){
                ((CTSException)(e.getCause())).printException();
            }else{
                CTSException argError = new CTSException(ExOther.argumentIllegal);
                argError.printException();
            }
        }
    }
}
