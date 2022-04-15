package CTSSystem;

import CTSException.CTSException;
import CTSException.*;
import User.User;
import User.UserSystem;

abstract public class Command {
    CTSSystem system = CTSSystem.getInstance();

    String[] args;

    int argumentNumber = -1;
    // 0 为默认， 1 为仅限管理员， 2 为仅限用户
    int permission = 0;
    public Command() {}
    public Command(int argumantNumber, int permission, String[] args){
        this.argumentNumber = argumantNumber;
        this.permission = permission;
        this.args = args;
    }
    public abstract void run() throws CTSException;
    public void checkArgumentNumber() throws CTSException{
        if(argumentNumber == -1) return;
        if(argumentNumber != args.length){
            throw new CTSException(ExOther.argumentIllegal);
        }
    }
    public void checkPermission() throws CTSException{
        if(permission == 0){
            return;
        }else if(permission == 1){
            if(system.isSuper() == false) throw new CTSException(ExOther.permissionSet);
        }else{
            if(system.isSuper() == true)throw new CTSException(ExOther.permissionSet);
        }
    }
}

class QUIT extends Command {
    public QUIT(String[] args) {
        super(0, 0, args);
    }
    @Override
    public void run() throws CTSException {
        system.setQuit();
        System.out.println("----- Good Bye! -----");
    }
}

class TunakTunakTun extends Command {
    public TunakTunakTun(String[] args) {
        super(0, 0, args);
    }
    @Override
    public void run() throws CTSException {
        if(!system.setSuper(true)) {
            throw new CTSException(ExOther.permissionSet);
        }
        System.out.println("DuluDulu");
    }
}

class NutKanutKanut extends Command {
    public NutKanutKanut(String[] args) {
        super(0, 0, args);
    }
    @Override
    public void run() throws CTSException {
        if(!system.setSuper(false)) {
            throw new CTSException(ExOther.permissionSet);
        }
        System.out.println("DaDaDa");
    }
}
class addUser extends Command {
    String name;
    String gender;
    String aadhaar;
    public addUser(String[] args) {
        super(3, 0, args);
    }
    UserSystem userSystem = UserSystem.getInstance();
    @Override
    public void run() throws CTSException {
//        system.addUser(args);
        name = args[0];
        gender = args[1];
        aadhaar = args[2];
        if (!User.isName(name)) throw new CTSException(ExUser.name);
        if (!User.isGender(gender)) throw new CTSException(ExUser.gender);
        if (!User.isAadhaar(aadhaar) || !User.aadhaarToGender(aadhaar).equals(gender)) {
            throw new CTSException(ExUser.aadhaarIllegal);
        }
        if (!userSystem.aadhaarIsRegister(aadhaar)) throw new CTSException(ExUser.aadhaarExist);
        userSystem.addUser(name, gender, aadhaar);
    }
}
class addLine extends Command {
    public addLine(String[] args) {
        super(-1, 0, args);
    }
    @Override
    public void run() throws CTSException {
        system.addLine(args);
    }
}
class delLine extends Command {
    public delLine(String[] args) {
        super(-1, 0, args);
    }
    @Override
    public void run() throws CTSException {
        system.delLine(args);
    }
}
class lineInfo extends Command {
    public lineInfo(String[] args) {
        super(-1, 0, args);
    }
    @Override
    public void run() throws CTSException {
        system.lineInfo(args);
    }
}
class listLine extends Command {
    public listLine(String[] args) {
        super(-1, 0, args);
    }
    @Override
    public void run() throws CTSException {
        system.listLine(args);
    }
}
class addStation extends Command {
    public addStation(String[] args) {
        super(-1, 0, args);
    }
    @Override
    public void run() throws CTSException {
        system.addStation(args);
    }
}
class delStation extends Command {
    public delStation(String[] args) {
        super(-1, 0, args);
    }
    @Override
    public void run() throws CTSException {
        system.delStation(args);
    }
}
class addTrain extends Command {
    public addTrain(String[] args) {
        super(-1, 0, args);
    }
    @Override
    public void run() throws CTSException {
        system.addTrain(args);
    }
}
class delTrain extends Command {
    public delTrain(String[] args) {
        super(-1, 0, args);
    }
    @Override
    public void run() throws CTSException {
        system.delTrain(args);
    }
}
class checkTicket extends Command {
    public checkTicket(String[] args) {
        super(-1, 0, args);
    }
    @Override
    public void run() throws CTSException {
        system.checkTicket(args);
    }
}
class listTrain extends Command {
    public listTrain(String[] args) {
        super(-1, 0, args);
    }
    @Override
    public void run() throws CTSException {
        system.listTrain(args);
    }
}

