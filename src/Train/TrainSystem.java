package Train;

import CTSException.*;
import Line.*;

import java.util.Collection;
import java.util.HashMap;

public class TrainSystem {
    static private HashMap<String, Train> trainsMap = new HashMap<String, Train>();

    static void checkIdIllegal(String id) throws CTSException {
        if(!id.matches("[G|K|0]\\d\\d\\d\\d")){
            throw new CTSException(ExTrain.trainId);
        }
    }

    static Train generateTrain(String id, int[] ticketNums, double[] ticketPrices) throws CTSException{
        checkIdIllegal(id);
        char type = id.toCharArray()[0];
        switch (type){
            case '0': return new NormalTrain(id, ticketNums, ticketPrices);
            case 'G': return new GatimaanTrain(id, ticketNums, ticketPrices);
            case 'K': return new KoyaTrain(id, ticketNums, ticketPrices);
        }
        throw new CTSException(ExTrain.trainId);
    }

    //注意，目前只能用于deltrain，不能用于checticket，异常信息不一样
    static Train getTrainById(String id) throws CTSException{
        Train ret = trainsMap.get(id);
        if(ret == null){
            throw new CTSException(ExTrain.trainNoExist);
        }
        return ret;
    }

    static public void addTrain(String id, String lineId, String[] ticketNumsStr, String[] ticketPricesStr) throws CTSException{
        checkIdIllegal(id);
        int typeNum = id.toCharArray()[0] == '0' || id.toCharArray()[0] == 'G' ? 3 : 2;
        if(ticketNumsStr.length != typeNum || ticketPricesStr.length != typeNum){
            throw new CTSException(ExOther.argumentIllegal);
        }
        if(trainsMap.get(id) != null){
            throw new CTSException(ExTrain.trainIdExist);
        }
        if(!Line.checkIdExist(lineId) || Line.getLineById(lineId).getRemainCapacity() <= 0){
            throw new CTSException(ExTrain.lineExistAndFree);
        }
        int[] ticketNums = new int[typeNum];
        double[] ticketPrices = new double[typeNum];
        int i = 0;
        for(String numStr : ticketNumsStr){
            int num;
            try{
                num = Integer.parseInt(numStr);
            }
            catch (Exception e){
                throw new CTSException(ExTrain.ticketNum);
            }
            if(num < 0){
                throw new CTSException(ExTrain.ticketNum);
            }
            ticketNums[i++] = num;
        }
        i = 0;
        for(String priceStr : ticketPricesStr){
            double price;
            try{
                price = Double.parseDouble(priceStr);
            }
            catch (Exception e){
                throw new CTSException(ExTrain.ticketPrice);
            }
            if(price < 0){
                throw new CTSException(ExTrain.ticketPrice);
            }
            ticketPrices[i++] = price;
        }

        Train newTrain = generateTrain(id, ticketNums, ticketPrices);
        trainsMap.put(id,newTrain);
        newTrain.setMyLine(Line.getLineById(lineId));
    }
    static public void deleteTrain(String id) throws CTSException{
        Train train = getTrainById(id);
        train.releaseMyLine();
        trainsMap.remove(id);
    }



    //票价相关
    static public void checkTicket(String id, String from, String to, String seat) throws CTSException{
        checkIdIllegal(id);
        Train train = trainsMap.get(id);
        if(train == null){
            throw new CTSException(ExTrain.trainIdNoExist);
        }
        Station station1 = train.getMyLine().getStationByName(from);
        Station station2 = train.getMyLine().getStationByName(to);
        if(!train.haveSeat(seat)){
            throw new CTSException(ExTrain.noHaveSeat);
        }
        Line line = train.getMyLine();
        int d1 = station1.getDistance();
        int d2 = station2.getDistance();
        String name1 = station1.getName();
        String name2 = station2.getName();
        System.out.printf("[%s: %s->%s] seat:%s remain:%d distance:%d price:%.2f",
                train.getId(), station1.getName(), station2.getName(),
                seat, train.getSeatNum(seat), d2 - d1, (d2 - d1) * train.getSeatPrice(seat) );
    }

    static public Collection<Train> getAllTrain(){
        return trainsMap.values();
    }

}
