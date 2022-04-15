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
    private CTSSystem(){
    }

    //系统退出功能实现
    private boolean isQuit = false;
    public void setQuit(){
        isQuit = true;
    }
    //超级管理员权限转换
    private boolean isSuper = false;
    public boolean setSuper(boolean isSuper){
        if(isSuper == this.isSuper) return false;
        this.isSuper = isSuper;
        return true;
    }


    //用户相关
    public void addUser(String[] args) throws CTSException {
        if(args.length != 3){
            throw new CTSException(ExOther.argumentIllegal);
        }
        User.addUser(args[0], args[1], args[2]);
    }

    //线路管理
    public void addLine(String[] args) throws CTSException{
        if(!isSuper) throw new CTSException(ExOther.commandNoExist);
        if(args.length % 2 == 1){
            throw new CTSException(ExOther.argumentIllegal);
        }
        int capacity;
        try{
            capacity = Integer.parseInt(args[1]);
        }
        catch (Exception e){
            throw new CTSException(ExOther.argumentIllegal);
        }
        int[] stationDistances = new int[args.length / 2 - 1];
        for(int i = 2, j = 0; i < args.length; i += 2, j++){
            int stationDistance;
            try{
                stationDistance = Integer.parseInt(args[i + 1]);
            }
            catch (Exception e){
                throw new CTSException(ExOther.argumentIllegal);
            }
            stationDistances[j] = stationDistance;
        }
        Line newLine = new Line(args[0], capacity);
        for(int i = 2, j = 0; i < args.length; i += 2, j++){
            String stationName = args[i];
            Station station = new Station(stationName, stationDistances[j]);
            newLine.addStation(station);
        }
        Line.addLine(newLine);
        System.out.println("Add Line success");
    }
    public void delLine(String[] args) throws CTSException{
        if(!isSuper) throw new CTSException(ExOther.commandNoExist);
        if(args.length != 1) throw new CTSException(ExOther.argumentIllegal);
        Line.deleteLine(args[0]);
        System.out.println("Del Line success");
    }
    public void lineInfo(String[] args) throws CTSException {
        if(args.length != 1) throw new CTSException(ExOther.argumentIllegal);
        System.out.println(Line.getLineById(args[0]));
    }
    public void listLine(String[] args) throws CTSException {
        if(args.length != 0) throw new CTSException(ExOther.argumentIllegal);
        Collection<Line> lines = Line.getAllLine();
        if(lines.size() == 0){
            System.out.println("No Lines");
            return;
        }
        int i = 1;
        for(Line line : lines){
            System.out.println("[" + Integer.toString(i++) + "] " + line);
        }
    }

    // 站点管理
    public void addStation(String[] args) throws CTSException{
        if(!isSuper) throw new CTSException(ExOther.commandNoExist);
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
        System.out.println("Add Station success");
    }
    public void delStation(String[] args) throws CTSException{
        if(!isSuper) throw new CTSException(ExOther.commandNoExist);
        if(args.length != 2) throw new CTSException(ExOther.argumentIllegal);
        Line line = Line.getLineById(args[0]);
        line.deleteStation(args[1]);
        System.out.println("Delete Station success");
    }

    //火车管理
    public void addTrain(String[] args) throws CTSException {
        if(!isSuper) throw new CTSException(ExOther.commandNoExist);
        if(args.length != 6 && args.length != 8) throw new CTSException(ExOther.argumentIllegal);
        int typeNum = args.length == 8 ? 3 : 2;
        String[] nums = new String[typeNum];
        String[] prices = new String[typeNum];
        for(int i = 0; i < typeNum; i++){
            prices[i] = args[i * 2 + 2];
            nums[i] = args[i * 2 + 3];
        }
        TrainSystem.addTrain(args[0], args[1], nums, prices);
        System.out.println("Add Train Success");
    }
    public void delTrain(String[] args) throws CTSException {
        if(!isSuper) throw new CTSException(ExOther.commandNoExist);
        if(args.length != 1) throw new CTSException(ExOther.argumentIllegal);
        TrainSystem.deleteTrain(args[0]);
        System.out.println("Del Train Success");
    }
    public void checkTicket(String[] args) throws CTSException {
        if(isSuper) throw new CTSException(ExOther.commandNoExist);
        if(args.length != 4) throw new CTSException(ExOther.argumentIllegal);
        TrainSystem.checkTicket(args[0], args[1], args[2], args[3]);
    }
    public void listTrain(String[] args) throws CTSException {
        if(args.length > 1) throw new CTSException(ExOther.argumentIllegal);
        Collection<Train> trains;
        if(args.length == 0){
            trains = TrainSystem.getAllTrain();
        }
        else{
            trains = Line.getLineById(args[0]).getAllTrain();
        }
        int i = 1;
        for(Train train : trains){
            System.out.println("[" + i++ + "] " + train);
        }
        if(trains.size() == 0){
            System.out.println("No Trains");
        }
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
            commandInstance = (Command)commandClass.getDeclaredConstructor().newInstance();
        }
        catch (Exception e){
            if(e instanceof ClassNotFoundException) {
                CTSException argError = new CTSException(ExOther.commandNoExist);
                argError.printException();
            }else{
                e.printStackTrace();
            }
            return;
        }
        try {
            commandInstance.checkArgumentNumber(args);
            commandInstance.checkPermission(isSuper);
            commandInstance.run(args);
        }
        catch (CTSException e){
            e.printException();
        }
        return;
        //        try{
//            Method method = CTSSystem.class.getDeclaredMethod(words[0],String[].class);
//            method.invoke(this, (Object)args);
//        }
//        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
//            if(e.getCause() instanceof CTSException){
//                ((CTSException)(e.getCause())).printException();
//            }
//            else if(e.getCause() == null){
//                CTSException argError = new CTSException(ExOther.commandNoExist);
//                argError.printException();
//            }
//            else{
//                System.out.println("系统内部出错");
//                e.printStackTrace();
//            }
//        }
    }



}
