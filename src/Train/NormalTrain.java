package Train;

import CTSException.*;

public class NormalTrain extends Train{
    NormalTrain (String serial) throws DebugException {
        super(serial, 3, "CC", "SB", "GG");
    }
}
