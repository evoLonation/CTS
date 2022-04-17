package Order;
import CTSException.*;
import Line.Station;
import Train.Train;
import User.*;

import java.util.Locale;

public class Order {

    private Train train;
    private User user;
    private Station from;
    private Station to;
    private String seat;
    private int num;

    private Order() {}

    public Train getTrain() {
        return train;
    }

    public User getUser() {
        return user;
    }

    public Station getFrom() {
        return from;
    }

    public Station getTo() {
        return to;
    }

    public String getSeat() {
        return seat;
    }

    public int getNum() {
        return num;
    }

    public static class makeNewOrder{
        private Train train;
        private User user;
        private Station from;
        private Station to;
        private String seat;
        private int num;
        public makeNewOrder(User user){
            this.user = user;
        }
        public makeNewOrder from(Station val) {
            this.from = val;
            return this;
        }
        public makeNewOrder to(Station val) {
            this.to = val;
            return this;
        }
        public makeNewOrder seat(String val) {
            this.seat = val;
            return this;
        }
        public makeNewOrder num(int val) {
            this.num = val;
            return this;
        }
        public makeNewOrder train(Train val) {
            this.train = val;
            return this;
        }
        public Order done() throws DebugException {
            if(!train.isSeatExist(seat))
                throw new DebugException(ExTrain.noHaveSeat);
            if(train.getRemainSeatNum(seat) < num)
                throw new DebugException(ExTrain.ticketNotEnough);
            if(!train.getMyLine().isStationNameExist(from.getName()) || !train.getMyLine().isStationNameExist(from.getName()))
                throw new DebugException(ExLine.stationNoExist);
            if(num <= 0)
                throw new DebugException(ExTrain.ticketNum);
            Order order = new Order(this);
            user.orders.add(order);
            train.myOrder.get(seat).add(order);
            return order;
        }
    }

    private Order(makeNewOrder Builder) {
        this.from = Builder.from;
        this.num = Builder.num;
        this.to = Builder.to;
        this.seat = Builder.seat;
        this.user = Builder.user;
        this.train = Builder.train;
    }

    @Override
    public String toString(){
        try {
            return String.format("[%s: %s->%s] seat:%s num:%d price:%.2f",train.getSerial(), from.getName(),
                    to.getName(), seat, num, train.getSeatPrice(seat) * num * (to.getDistance() - from.getDistance()) );
        } catch (DebugException e) {
            e.printStackTrace();
        }
        return null;
    }
}
