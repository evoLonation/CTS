package Train;

import CTSException.*;
import Line.Line;

import java.util.Formatter;
import java.util.HashMap;
import java.util.TreeSet;

public abstract class Train implements Comparable{

    static private HashMap<String, Train> trainsMap = new HashMap<String, Train>();

    //车次
    private String id;
    private int[] ticketNums;
    private double[] ticketPrices;
    private Line myLine;
    protected HashMap<String, Integer> trainSeatMap = new HashMap<String, Integer>();
    protected String[] trainSeatArr;

    //仅用于addTrain
    Train(String id, int[] ticketNums, double[] ticketPrices, String... seats) throws CTSException {
        TrainSystem.checkIdIllegal(id);
        this.id = id;
        int typeNum = id.toCharArray()[0] == '0' || id.toCharArray()[0] == 'G' ? 3 : 2;
        if(ticketNums.length != typeNum || ticketPrices.length != typeNum || seats.length != typeNum){
            throw new CTSException(ExOther.argumentIllegal);
        }
        for(int num : ticketNums){
            if(num < 0){
                throw new CTSException(ExTrain.ticketNum);
            }
        }
        for(double price : ticketPrices){
            if(price < 0){
                throw new CTSException(ExTrain.ticketPrice);
            }
        }
        this.ticketNums = ticketNums;
        this.ticketPrices = ticketPrices;
        this.trainSeatArr = seats;
        for(int i = 0; i < typeNum; i++)
            trainSeatMap.put(seats[i], i);
    }

    String getId(){
        return id;
    }
    Line getMyLine() throws CTSException{
        if(myLine == null){
            throw new CTSException(ExTrain.noLineWith);
        }
        return myLine;
    }

    void setMyLine(Line line) throws CTSException{
        if(this.myLine != null){
            throw new CTSException(ExTrain.alreadySetLine);
        }
        line.giveKeyTo(this);
        key.addTrain(this);
        this.myLine = line;
    }

    private Line.ToTrain key;
    public void receiveKey(Line.ToTrain key){
        this.key = key;
    }

    void releaseMyLine() throws CTSException{
        if(this.myLine == null){
            throw new CTSException(ExTrain.noLineWith);
        }
        this.myLine = null;
        key.deleteTrain(this);
    }


    //票价相关
    boolean haveSeat(String seat) {
        return trainSeatMap.get(seat) != null;
    }
    int getSeatNum(String seat) throws CTSException {
        if(!haveSeat(seat))throw new CTSException(ExTrain.noHaveSeat);
        return ticketNums[(int)trainSeatMap.get(seat)];
    }
    double getSeatPrice(String seat) throws CTSException {
        if(!haveSeat(seat))throw new CTSException(ExTrain.noHaveSeat);
        return ticketPrices[(int)trainSeatMap.get(seat)];
    }


    @Override
    public String toString() {
        String ret = String.format("%s: %s", id, myLine.getId());

        for(int i = 0; i < ticketPrices.length; i++){
            ret += String.format(" [%s]%.2f:%d", trainSeatArr[i], ticketPrices[i], ticketNums[i]);
        }
        return  ret;
    }

    @Override
    public int compareTo(Object o) {
        int type1 = this instanceof KoyaTrain ? 1 :
                this instanceof GatimaanTrain ? 2 : 3;
        int type2 = o instanceof KoyaTrain ? 1 :
                o instanceof GatimaanTrain ? 2 : 3;
        if(type1 == type2){
            return this.id.compareTo(((Train)o).id);
        }
        return type1 - type2;
    }
}
