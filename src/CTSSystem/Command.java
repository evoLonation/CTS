package CTSSystem;

import CTSException.CTSException;
import CTSException.*;
abstract public class Command {
    int argumentNumber = -1;
    // 0 为默认， 1 为仅限管理员， 2 为仅限用户
    int permission = 0;
    public abstract void run(String [] args) throws CTSException;
    public void checkArgumentNumber(String [] args) throws CTSException{
        if(argumentNumber == -1) return;
        if(argumentNumber != args.length){
            throw new CTSException(ExOther.argumentIllegal);
        }
    }
    public void checkPermission(boolean isSuper) throws CTSException{
        if(permission == 0){
            return;
        }else if(permission == 1){
            if(isSuper == false) throw new CTSException(ExOther.permissionSet);
        }else{
            if(isSuper == true)throw new CTSException(ExOther.permissionSet);
        }
    }
}

class QUIT extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        system.setQuit();
        System.out.println("----- Good Bye! -----");
    }
}
class TunakTunakTun extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        if(!system.setSuper(true)) {
            throw new CTSException(ExOther.permissionSet);
        }
        System.out.println("DuluDulu");
    }
}
class NutKanutKanut extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        if(!system.setSuper(false)) {
            throw new CTSException(ExOther.permissionSet);
        }
        System.out.println("DaDaDa");
    }
}
class addUser extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        system.addUser(args);
    }
}
class addLine extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        system.addLine(args);
    }
}
class delLine extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        system.delLine(args);
    }
}
class lineInfo extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        system.lineInfo(args);
    }
}
class listLine extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        system.listLine(args);
    }
}
class addStation extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        system.addStation(args);
    }
}
class delStation extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        system.delStation(args);
    }
}
class addTrain extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        system.addTrain(args);
    }
}
class delTrain extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        system.delTrain(args);
    }
}
class checkTicket extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        system.checkTicket(args);
    }
}
class listTrain extends Command {
    CTSSystem system = CTSSystem.getInstance();
    @Override
    public void run(String[] args) throws CTSException {
        system.listTrain(args);
    }
}

