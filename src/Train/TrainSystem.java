package Train;

import CTSException.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;

public class TrainSystem {
    //单例类实现
    static private TrainSystem onlyInstance = new TrainSystem();
    static public TrainSystem getInstance(){
        return onlyInstance;
    }
    private TrainSystem() {}

    // 火车储存
    static private HashMap<String, Train> trainBySerial = new HashMap<String, Train>();
    static private TreeSet<Train> trainSet = new TreeSet<Train>();

    public void addTrain(Train newTrain) throws DebugException {
        if(isTrainSerialExist(newTrain.getSerial()))
            throw new DebugException(ExTrain.trainSerialExist);
        trainBySerial.put(newTrain.getSerial(), newTrain);
        trainSet.add(newTrain);
    }

    public void deleteTrain(Train train) throws DebugException {
        if(!isTrainSerialExist(train.getSerial()))
            throw new DebugException(ExTrain.trainNoExist);
        trainSet.remove(train);
        trainBySerial.remove(train.getSerial());
    }

    public Train getTrainBySerial(String id) throws DebugException{
        Train ret = trainBySerial.get(id);
        if(ret == null){
            throw new DebugException(ExTrain.trainNoExist);
        }
        return ret;
    }

    static public Collection<Train> getAllTrain(){
        return trainSet;
    }

    public boolean isTrainSerialExist(String trainSerial) {
        return trainBySerial.get(trainSerial) != null;
    }

}
