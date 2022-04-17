package Line;

import CTSException.*;

public class Station implements Comparable{
    private String name;
    private int distance;
    public Station(){
        this.name = "Delhi-3";
        this.distance = 0;
    }
    public Station(String name, int distance) throws DebugException {
        if(distance <= 0)
            throw new DebugException(ExOther.argumentIllegal);
        if(name == null)
            throw new DebugException(ExOther.objectIsNull);
        this.name = name;
        this.distance = distance;
    }
    public String getName() {
        return name;
    }
    public int getDistance() {
        return distance;
    }

    @Override
    public int compareTo(Object o) {
        if(this.getDistance() == ((Station)o).getDistance()){
            return this.getName().compareTo(((Station)o).getName());
        }
        return this.getDistance() - ((Station)o).getDistance();
    }
}