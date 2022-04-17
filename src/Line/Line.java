package Line;

import CTSException.CTSException;
import CTSException.*;
import Train.Train;

import java.util.*;

public class Line {

    private String id;
    private int loadCapacity;
    private TreeMap<String, Station> stationByName = new TreeMap<>();

    private TreeSet<Station> stationSet = new TreeSet<>();
    private TreeSet<Train> trainSet = new TreeSet<Train>();


    public Line(String id, int loadCapacity) throws DebugException{
        if(loadCapacity <= 0){
            throw new DebugException(ExLine.capacity);
        }
        this.id = id;
        this.loadCapacity = loadCapacity;
        Station startStation = new Station();
        addStation(startStation);
    }

    public String getId(){
        return id;
    }
    public int getRemainCapacity(){
        return loadCapacity - trainSet.size();
    }
    public Station getStationByName(String name) throws DebugException{
        Station station = stationByName.get(name);
        if(station == null){
            throw new DebugException(ExLine.stationNoExist);
        }
        return station;
    }
    public Collection<Train> getAllTrain(){
        return trainSet;
    }

    public boolean isStationNameExist(String name) {
        Station station = stationByName.get(name);
        return station != null;
    }


    public void addStation(Station station) throws DebugException {
        if(stationByName.get(station.getName()) != null){
            throw new DebugException(ExLine.stationExist);
        }
        stationByName.put(station.getName(), station);
        stationSet.add(station);
    }
    public void deleteStation(String stationName) throws DebugException {
        if(!isStationNameExist(stationName)){
            throw new DebugException(ExLine.stationNoExist);
        }
        Station station = stationByName.get(stationName);
        stationByName.remove(stationName);
        stationSet.remove(station);
    }

    public void addTrain(Train newTrain) throws DebugException {
        if(trainSet.size() >= loadCapacity){
            throw new DebugException(ExTrain.lineExistAndFree);
        }
        trainSet.add(newTrain);
        newTrain.giveKeyTo(this);
        key.setMyLine(this);
    }
    public void deleteTrain(Train train) throws DebugException{
        if(!trainSet.remove(train)){
            throw new DebugException(ExLine.NotHaveTheTrain);
        }
        train.giveKeyTo(this);
        key.releaseMyLine();
    }

    private Train.ToLine key;
    public void receiveKey(Train.ToLine key){
        this.key = key;
    }



    @Override
    public String toString() {
        String ret = id + " " + trainSet.size() + "/" + loadCapacity;
        Iterator<Station> it = stationSet.iterator();
        it.next();
        while(it.hasNext()) {
            Station station = it.next();
            ret += " " + station.getName() + ":" + station.getDistance();
        }
        return ret;
    }
}

