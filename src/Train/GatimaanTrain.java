package Train;

import CTSException.*;

public class GatimaanTrain extends Train{
    GatimaanTrain (String serial) throws DebugException {
        super(serial, 3, "SC", "HC", "SB");
    }
}
