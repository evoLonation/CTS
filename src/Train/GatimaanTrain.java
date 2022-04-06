package Train;

import CTSException.*;

public class GatimaanTrain extends Train{
    GatimaanTrain(String id, int[] ticketNums, double[] ticketPrices) throws CTSException {
        super(id, ticketNums, ticketPrices, "SC", "HC", "SB");
    }
}
