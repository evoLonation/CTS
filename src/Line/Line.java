package Line;

import CTSException.CTSException;
import CTSException.*;
import Train.Train;

import java.util.*;

public class Line {
    private static TreeMap<String, Line> lines = new TreeMap<String, Line>();

    private String id;
    private int loadCapacity;
    private int lastNum = 0;
//    TreeMap<Integer, Station> stationsByDistance = new TreeMap<Integer, Station>();
    private TreeMap<String, Station> stationByName = new TreeMap<>();

    class StationCompare implements Comparator{
        @Override
        public int compare(Object o1, Object o2) {
            if(((Station)o1).getDistance() == ((Station)o2).getDistance()){
                return ((Station)o1).getName().compareTo(((Station)o2).getName());
            }
            return ((Station)o1).getDistance() - ((Station)o2).getDistance();
        }
    }
    private TreeSet<Station> stationSet = new TreeSet<>(new StationCompare());
    private ArrayList<Train> trains = new ArrayList<Train>();


    public Line(String id, int loadCapacity) throws CTSException{
        if(loadCapacity <= 0){
            throw new CTSException(ExLine.capacity);
        }
        this.id = id;
        this.loadCapacity = loadCapacity;
        Station startStation = new Station();
        addStation(startStation);
    }

    public String getId(){
        return id;
    }

    static public boolean checkIdExist(String id){
        Line ret = lines.get(id);
        return ret != null;
    }
    static public Line getLineById(String id) throws CTSException{
        Line ret = lines.get(id);
        if(ret == null){
            throw new CTSException(ExLine.lineNoExist);
        }
        return ret;
    }

    public Station getStationByName(String name) throws CTSException{
        Station station = stationByName.get(name);
        if(station == null){
            throw new CTSException(ExLine.stationNoExist);
        }
        return station;
    }
    public int getRemainCapacity(){
        return loadCapacity - trains.size();
    }
    static public Collection<Line> getAllLine(){
        return lines.values();
    }

    public void addStation(Station station) throws CTSException {
        if(stationByName.get(station.getName()) != null){
            throw new CTSException(ExLine.stationExist);
        }
        stationByName.put(station.getName(), station);
        stationSet.add(station);
    }
    public void deleteStation(String stationName) throws CTSException {
        Station station = stationByName.get(stationName);
        if(station == null){
            throw new CTSException(ExLine.stationNoExist);
        }
        stationByName.remove(stationName);
        stationSet.remove(station);
    }

    static public void addLine(Line line) throws CTSException{
        if(lines.get(line.id) != null){
            throw new CTSException(ExLine.lineExist);
        }
        lines.put(line.id, line);
    }
    static public void deleteLine(String lineId) throws CTSException{
        if(lines.get(lineId) == null){
            throw new CTSException(ExLine.lineNoExist);
        }
        lines.remove(lineId);
    }

    //给Train类的友元函数
    public class ToTrain{
        public void addTrain(Train train) throws CTSException{
            if(trains.size() >= loadCapacity){
                throw new CTSException(ExTrain.lineExistAndFree);
            }
            trains.add(train);
        }
        public void deleteTrain(Train train) throws CTSException{
            if(!trains.remove(train)){
                throw new CTSException(ExLine.NotHaveTheTrain);
            }
        }
    }
    public void giveKeyTo(Train other) {
        other.receiveKey(new ToTrain());
    }


    public ArrayList<Train> getAllTrain(){
        return trains;
    }

    @Override
    public String toString() {
        String ret = id + " " + trains.size() + "/" + loadCapacity;
        Iterator<Station> it = stationSet.iterator();
        it.next();
        while(it.hasNext()) {
            Station station = it.next();
            ret += " " + station.getName() + ":" + station.getDistance();
        }
        return ret;
    }
}

