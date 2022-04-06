package Train;

import CTSException.*;

public class KoyaTrain extends Train {
    KoyaTrain(String id, int[] ticketNums, double[] ticketPrices) throws CTSException {
        super(id, ticketNums, ticketPrices, "1A", "2A");
    }
}
