package Train;

import CTSException.*;

public class NormalTrain extends Train{
    NormalTrain(String id, int[] ticketNums, double[] ticketPrices) throws CTSException {
        super(id, ticketNums, ticketPrices, "CC", "SB", "GG");
    }


}
