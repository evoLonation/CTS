package Train;

import CTSException.*;
import Line.Line;
import Order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public abstract class Train implements Comparable{

    private String serial;

    int typeNum;
    private Line myLine;
    protected HashMap<String, Integer> trainSeatMap = new HashMap<String, Integer>();
    protected String[] trainSeatArr;

    protected Train(String serial, int typeNum, String... seats){
        this.typeNum = typeNum;
        this.serial = serial;
        ticketPrices = new double[typeNum];
        ticketNums = new int[typeNum];
        this.trainSeatArr = seats;
        for(int i = 0; i < typeNum; i++)
            trainSeatMap.put(seats[i], i);
        for(int i = 0; i < typeNum; i++)
            myOrder.put(seats[i], new HashSet<Order>());
    }
    static public Train TrainFactory(String trainSerial) throws DebugException{
        if(!isSerial(trainSerial))
            throw new DebugException(ExTrain.trainSerial);
        Train newTrain;
        switch (Train.getTypeBySerial(trainSerial)){
            case '0': newTrain = new NormalTrain(trainSerial);break;
            case 'G': newTrain = new GatimaanTrain(trainSerial);break;
            case 'K': newTrain = new KoyaTrain(trainSerial);break;
            default: throw new DebugException(ExOther.other);
        }
        return newTrain;
    }


    public int getTypeNum(){
        return typeNum;
    }

    public String getSerial(){
        return serial;
    }

    public Line getMyLine() throws DebugException{
        if(myLine == null){
            throw new DebugException(ExTrain.noLineWith);
        }
        return myLine;
    }





    public void deleteSelf() throws DebugException {
        myLine.deleteTrain(this);
        TrainSystem.getInstance().deleteTrain(this);
    }

    //票价相关
    public boolean isSeatExist(String seat) {
        return trainSeatMap.get(seat) != null;
    }

    public class ToLine {
        public void setMyLine(Line line) throws DebugException{
            if(myLine != null){
                throw new DebugException(ExTrain.alreadySetLine);
            }
            myLine = line;
        }
        public void releaseMyLine() throws DebugException{
            if(myLine == null){
                throw new DebugException(ExTrain.noLineWith);
            }
            myLine = null;
        }

    }
    public void giveKeyTo(Line other) {
        other.receiveKey(new Train.ToLine());
    }




    static public boolean isSerial(String trainSerial){
        return trainSerial.matches("[G|K|0]\\d\\d\\d\\d");
    }

    static public char getTypeBySerial(String serial) throws DebugException{
        if(!Train.isSerial(serial))
            throw new DebugException(ExTrain.trainSerial);
        return serial.toCharArray()[0];
    }

    //购买相关
    private int[] ticketNums;
    private double[] ticketPrices;
    public HashMap<String, HashSet<Order>> myOrder = new HashMap<>();

    public int getRemainSeatNum(String seat) throws DebugException {
        if(!isSeatExist(seat))throw new DebugException(ExTrain.noHaveSeat);
        int haveBuy = 0;
        HashSet<Order> orders = myOrder.get(seat);
        for(Order o : orders) {
            haveBuy += o.getNum();
        }
        return ticketNums[trainSeatMap.get(seat)] - haveBuy;
    }

    public double getSeatPrice(String seat) throws DebugException {
        if(!isSeatExist(seat))throw new DebugException(ExTrain.noHaveSeat);
        return ticketPrices[(int)trainSeatMap.get(seat)];
    }

    public void setTicketPrices(double[] ticketPrices) throws DebugException{
        if(ticketPrices.length != typeNum)
            throw new DebugException(ExTrain.typeNumNoMatch);
        this.ticketPrices = ticketPrices.clone();
    }

    public void setTicketNums(int[] ticketNums) throws DebugException {
        if(ticketNums.length != typeNum)
            throw new DebugException(ExTrain.typeNumNoMatch);
        this.ticketNums = ticketNums.clone();
    }




    @Override
    public int compareTo(Object o) {
        int type1 = this instanceof KoyaTrain ? 1 :
                this instanceof GatimaanTrain ? 2 : 3;
        int type2 = o instanceof KoyaTrain ? 1 :
                o instanceof GatimaanTrain ? 2 : 3;
        if(type1 == type2){
            return this.serial.compareTo(((Train)o).serial);
        }
        return type1 - type2;
    }

    @Override
    public String toString() {
        String ret = String.format("%s: %s", serial, myLine.getId());

        for(int i = 0; i < ticketPrices.length; i++){
            ret += String.format(" [%s]%.2f:%d", trainSeatArr[i], ticketPrices[i], ticketNums[i]);
        }
        return  ret;
    }

}
