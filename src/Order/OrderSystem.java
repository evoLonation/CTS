package Order;

import Line.Station;
import Train.Train;
import Train.TrainSystem;
import User.User;

public class OrderSystem {
    //单例类实现
    static private OrderSystem onlyInstance = new OrderSystem();
    static public OrderSystem getInstance(){
        return onlyInstance;
    }
    private OrderSystem() {}


}
