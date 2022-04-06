package Line;

import CTSException.*;

public class Station{
    //    int order;
    private String name;
    private int distance;
    public Station(){
        this.name = "Delhi-3";
        this.distance = 0;
    }
    public Station(String name, int distance) throws CTSException {
        if(distance <= 0){
            throw new CTSException(ExOther.argumentIllegal);
        }
//        this.order = order;
        this.name = name;
        this.distance = distance;
    }
    public String getName() {
        return name;
    }
    public int getDistance() {
        return distance;
    }


}