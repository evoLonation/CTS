package Line;

import CTSException.CTSException;
import CTSException.*;

import java.util.*;

public class Line {
    private static TreeMap<String, Line> lines = new TreeMap<String, Line>();

    private String id;
    private int loadCapacity;
    private int alreadyLoad;
    private int lastNum = 0;
//    TreeMap<Integer, Station> stationsByDistance = new TreeMap<Integer, Station>();
    TreeMap<String, Integer> stationNameOrder = new TreeMap<String, Integer>();
    TreeMap<Integer, Station> stationsByOrder = new TreeMap<Integer, Station>();


    public Line(String id, int loadCapacity) throws CTSException{
        if(loadCapacity <= 0){
            throw new CTSException(ExLine.capacity);
        }
        this.id = id;
        this.loadCapacity = loadCapacity;
        stationsByOrder.put(lastNum, new Station());
        stationNameOrder.put("Delhi-3", lastNum);
        lastNum ++;
    }

    static public Line getLineById(String id) throws CTSException{
        Line ret = lines.get(id);
        if(ret == null){
            throw new CTSException(ExLine.lineNoExist);
        }
        return ret;
    }
    static public Collection<Line> getAllLine(){
        return lines.values();
    }

    public void addStation(Station station) throws CTSException {
        if(stationNameOrder.get(station.getName()) != null){
            throw new CTSException(ExLine.stationExist);
        }
        stationsByOrder.put(lastNum, station);
        stationNameOrder.put(station.getName(), lastNum);
        lastNum++ ;
    }
    public void deleteStation(String stationName) throws CTSException {
        Integer order = stationNameOrder.get(stationName);
        if(order == null){
            throw new CTSException(ExLine.stationNoExist);
        }
        stationNameOrder.remove(stationName);
        stationsByOrder.remove(order);
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



    @Override
    public String toString() {
        String ret = id + " " + alreadyLoad + "/" + loadCapacity;
        Collection<Station> stations = stationsByOrder.values();
        Iterator<Station> it = stations.iterator();
        it.next();
        while(it.hasNext()) {
            Station station = it.next();
            ret += " " + station.getName() + ":" + station.getDistance();
        }
        return ret;
    }
}

