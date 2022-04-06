package Train;
import CTSException.*;

public class Ticket {
    private double price;
    Ticket(double price) throws CTSException{
        if(price < 0){
            throw new CTSException(ExTrain.ticketPrice);
        }
        this.price = price;
    }

    public void setPrice(double price) throws CTSException{
        if(price < 0){
            throw new CTSException(ExTrain.ticketPrice);
        }
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
