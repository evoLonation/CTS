package Train;

import CTSException.*;

public class KoyaTrain extends Train {
    KoyaTrain (String serial) throws DebugException {
        super(serial, 2, "1A", "2A");
    }
}
