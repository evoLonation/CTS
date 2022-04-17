package CTSSystem;

import CTSException.CTSException;
import CTSException.*;
import Line.*;
import Order.Order;
import Train.Train;
import Train.TrainSystem;
import User.User;
import User.UserSystem;

import java.util.Collection;
import java.util.HashSet;

abstract public class Command {
    private String finalMessage = null;
    protected String[] args;
    private int argumentNumber = -1;
    protected Command() {}
    public Command(String[] args, int argumentNumber, String finalMessage){
        this.argumentNumber = argumentNumber;
        this.args = args;
        this.finalMessage = finalMessage;
    }
    public Command(String[] args, int argumentNumber){
        this.argumentNumber = argumentNumber;
        this.args = args;
    }
    public Command(String[] args){
        this.args = args;
    }
    public Command(String[] args, String successMessage){
        this.finalMessage = successMessage;
        this.args = args;
    }
    protected void setFinalMessage(String finalMessage) {
        this.finalMessage = finalMessage;
    }
    public void execute() throws CTSException {
        checkArgumentNumber();
        preprocess();
        run();
        finalPrint();
    }

    final void checkArgumentNumber() throws CTSException{
        if(argumentNumber == -1) return;
        else if(argumentNumber != args.length)
            throw new CTSException(ExOther.argumentIllegal);
    }
    void preprocess() throws CTSException{}
    void run() throws CTSException{}
    void finalPrint(){
        if(finalMessage != null)
            System.out.println(finalMessage);
    }

}

abstract class SuperCommand extends Command {
    protected SuperCommand() {}
    public SuperCommand(String[] args, int argumentNumber, String finalMessage){
        super(args, argumentNumber, finalMessage);
    }
    public SuperCommand(String[] args, int argumentNumber){
        super(args, argumentNumber);
    }
    public SuperCommand(String[] args){
        super(args);
    }
    public SuperCommand(String[] args, String successMessage){
        super(args, successMessage);
    }
    @Override
    public void execute() throws CTSException {
        if(!CTSSystem.getInstance().isSuper())throw new CTSException(ExOther.commandNoExist);
        super.execute();
    }
}
abstract class UserCommand extends Command {
    protected UserCommand() {}
    public UserCommand(String[] args, int argumentNumber, String finalMessage){
        super(args, argumentNumber, finalMessage);
    }
    public UserCommand(String[] args, int argumentNumber){
        super(args, argumentNumber);
    }
    public UserCommand(String[] args){
        super(args);
    }
    public UserCommand(String[] args, String successMessage){
        super(args, successMessage);
    }
    @Override
    public void execute() throws CTSException {
        if(CTSSystem.getInstance().isSuper())throw new CTSException(ExOther.commandNoExist);
        super.execute();
    }
}

abstract class LoginCommand extends Command implements ToSystem {
    protected LoginCommand() {}
    public LoginCommand(String[] args, int argumentNumber, String finalMessage){
        super(args, argumentNumber, finalMessage);
    }
    public LoginCommand(String[] args, int argumentNumber){
        super(args, argumentNumber);
    }
    public LoginCommand(String[] args){
        super(args);
    }
    public LoginCommand(String[] args, String successMessage){
        super(args, successMessage);
    }
    @Override
    public void execute() throws CTSException {
        checkArgumentNumber();
        if(!system.isLogin())
            throw new CTSException(ExOther.pleaseLoginFirst);
        preprocess();
        run();
        finalPrint();
    }
}

interface ToSystem {
    CTSSystem system = CTSSystem.getInstance();
}
interface ToLine {
    LineSystem lineSystem = LineSystem.getInstance();
}
interface ToUser {
    UserSystem userSystem = UserSystem.getInstance();
}
interface ToTrain {
    TrainSystem trainSystem = TrainSystem.getInstance();
}

class QUIT extends Command implements ToSystem {
    public QUIT(String[] args) {
        super(args, 0, "----- Good Bye! -----");
    }
    @Override
    public void run() throws CTSException {
        system.setQuit();
    }
}

class TunakTunakTun extends Command implements ToSystem{
    public TunakTunakTun(String[] args) {
        super(args, 0, "DuluDulu");
    }
    @Override
    void preprocess() throws CTSException {
        if(system.isSuper()){
            throw new CTSException(ExOther.permissionSet);
        }
    }
    @Override
    public void run() throws CTSException {
        system.setSuper(true);
    }
}

class NutKanutKanut extends Command implements ToSystem{
    public NutKanutKanut(String[] args) {
        super(args, 0, "DaDaDa");
    }

    @Override
    void preprocess() throws CTSException {
        if(!system.isSuper()){
            throw new CTSException(ExOther.permissionSet);
        }
    }
    @Override
    public void run() throws CTSException {
        system.setSuper(false);
    }
}

class addUser extends Command implements ToUser{
    String name;
    String gender;
    String aadhaar;
    User newUser;
    public addUser(String[] args) {
        super(args,3);
    }

    @Override
    void preprocess() throws CTSException {
        name = args[0];
        gender = args[1];
        aadhaar = args[2];
        if (!User.isName(name)) throw new CTSException(ExUser.name);
        if (!User.isGender(gender)) throw new CTSException(ExUser.gender);
        if (!User.isAadhaar(aadhaar) || !User.aadhaarToGender(aadhaar).equals(gender)) {
            throw new CTSException(ExUser.aadhaarIllegal);
        }
        if (userSystem.aadhaarIsRegister(aadhaar)) throw new CTSException(ExUser.aadhaarExist);
    }
    @Override
    public void run() throws CTSException {
        newUser = new User(name, gender, aadhaar);
        userSystem.addUser(newUser);
        setFinalMessage(newUser.toString());
    }

}

class addLine extends SuperCommand implements ToLine {
    int capacity;
    int[] stationDistances;
    Line newLine;
    public addLine(String[] args) {
        super(args, "Add Line success");
    }

    @Override
    void preprocess() throws CTSException {
        if(args.length < 3 || args.length % 2 == 1){
            throw new CTSException(ExOther.argumentIllegal);
        }
        try{
            capacity = Integer.parseInt(args[1]);
        }
        catch (Exception e){
            throw new CTSException(ExOther.argumentIllegal);
        }
        stationDistances = new int[args.length / 2 - 1];
        for(int i = 2, j = 0; i < args.length; i += 2, j++){
            int stationDistance;
            try{
                stationDistance = Integer.parseInt(args[i + 1]);
            }
            catch (Exception e){
                throw new CTSException(ExOther.argumentIllegal);
            }
            if(stationDistance <= 0)
                throw new CTSException(ExOther.argumentIllegal);
            stationDistances[j] = stationDistance;
        }
        HashSet<String> stationNames = new HashSet<>();
        for(int i = 2, j = 0; i < args.length; i += 2, j++){
            String stationName = args[i];
            if(!stationNames.add(stationName))
                throw new CTSException(ExLine.stationExist);
        }
        if(lineSystem.isLineIdExist(args[0]))
            throw new CTSException(ExLine.lineExist);
        if(capacity <= 0)
            throw new CTSException(ExLine.capacity);
    }

    @Override
    public void run() throws CTSException {
        newLine = new Line(args[0], capacity);
        for(int i = 2, j = 0; i < args.length; i += 2, j++){
            String stationName = args[i];
            Station station = new Station(stationName, stationDistances[j]);
            newLine.addStation(station);
        }
        lineSystem.addLine(newLine);
    }
}

class delLine extends SuperCommand implements ToLine{
    public delLine(String[] args) {
        super(args, 1, "Del Line success");
    }

    @Override
    void preprocess() throws CTSException {
        if(!lineSystem.isLineIdExist(args[0]))
            throw new CTSException(ExLine.lineNoExist);
    }
    @Override
    public void run() throws CTSException {
        lineSystem.deleteLine(args[0]);
    }
}

class lineInfo extends Command implements ToLine{
    public lineInfo(String[] args) {
        super(args, 1);
    }

    @Override
    void preprocess() throws CTSException {
        if(!lineSystem.isLineIdExist(args[0]))
            throw new CTSException(ExLine.lineNoExist);
    }
    @Override
    public void run() throws CTSException {
        setFinalMessage(lineSystem.getLineById(args[0]).toString());
    }
}

class listLine extends Command implements ToLine {
    Collection<Line> lines;
    public listLine(String[] args) {
        super(args, 0);
    }

    @Override
    public void finalPrint() {
        lines = lineSystem.getAllLine();
        if (lines.size() == 0) {
            System.out.println("No Lines");
            return;
        }
        int i = 1;
        for (Line line : lines) {
            System.out.println("[" + Integer.toString(i++) + "] " + line);
        }
    }
}

class addStation extends SuperCommand implements ToLine {
    Line line;
    String lineId;
    String newStationName;
    int newStationDistance;
    Station newStation;
    public addStation(String[] args) {
        super(args, 3, "Add Station success");
    }

    @Override
    void preprocess() throws CTSException {
        lineId = args[0];
        newStationName = args[1];
        try{
            newStationDistance = Integer.parseInt(args[2]);
        }
        catch (Exception e){
            throw new CTSException(ExOther.argumentIllegal);
        }
        if(!lineSystem.isLineIdExist(lineId))
            throw new CTSException(ExLine.lineNoExist);
        line = lineSystem.getLineById(lineId);
        if(line.isStationNameExist(newStationName))
            throw new CTSException(ExLine.stationExist);
        if(newStationDistance <= 0)
            throw new CTSException(ExOther.argumentIllegal);
    }

    @Override
    public void run() throws CTSException {
        newStation = new Station(newStationName, newStationDistance);
        line.addStation(newStation);
    }
}

class delStation extends SuperCommand implements ToLine {
    String lineId;
    String stationName;
    Line line;
    public delStation(String[] args) {
        super(args, 2, "Delete Station success");
    }

    @Override
    void preprocess() throws CTSException {
        lineId = args[0];
        stationName = args[1];
        if(!lineSystem.isLineIdExist(lineId))
            throw new CTSException(ExLine.lineNoExist);
        line = lineSystem.getLineById(lineId);
        if(!line.isStationNameExist(stationName))
            throw new CTSException(ExLine.stationNoExist);
    }

    @Override
    public void run() throws CTSException {
        line.deleteStation(stationName);
    }
}
class addTrain extends SuperCommand implements ToTrain, ToLine{
    String trainSerial;
    String lineId;
    double[] ticketPrices;
    int[] ticketNums;
    Train newTrain;
    Line line;
    public addTrain(String[] args) {
        super(args, "Add Train Success");
    }

    @Override
    void preprocess() throws CTSException {
        if(args.length != 6 && args.length != 8)
            throw new CTSException(ExOther.argumentIllegal);
        trainSerial = args[0];
        lineId = args[1];
        if(!Train.isSerial(trainSerial))
            throw new CTSException(ExTrain.trainSerial);
        if(trainSystem.isTrainSerialExist(trainSerial))
            throw new CTSException(ExTrain.trainSerialExist);
        newTrain = Train.TrainFactory(trainSerial);


        if(newTrain.getTypeNum() != (args.length - 2) / 2)
            throw new CTSException(ExOther.argumentIllegal);

        if(!lineSystem.isLineIdExist(lineId) || lineSystem.getLineById(lineId).getRemainCapacity() <= 0)
            throw new CTSException(ExTrain.lineExistAndFree);
        line = lineSystem.getLineById(lineId);

        ticketPrices = new double[newTrain.getTypeNum()];
        ticketNums = new int[newTrain.getTypeNum()];

        for(int i = 2, j = 0; i < args.length; i += 2, j++){
            try{
                ticketPrices[j] = Double.parseDouble(args[i]);
            }
            catch (Exception e){
                throw new CTSException(ExTrain.ticketPrice);
            }
            if(ticketPrices[j] < 0){
                throw new CTSException(ExTrain.ticketPrice);
            }
        }
        for(int i = 3, j = 0; i < args.length; i += 2, j++){
            try{
                ticketNums[j] = Integer.parseInt(args[i]);
            }
            catch (Exception e){
                throw new CTSException(ExTrain.ticketNum);
            }
            if(ticketNums[j] < 0){
                throw new CTSException(ExTrain.ticketNum);
            }
        }
        newTrain.setTicketPrices(ticketPrices);
        newTrain.setTicketNums(ticketNums);
    }

    @Override
    public void run() throws CTSException {
        line.addTrain(newTrain);
        trainSystem.addTrain(newTrain);
    }
}
class delTrain extends SuperCommand implements ToLine, ToTrain {
    String trainSerial;
    Train train;
    public delTrain(String[] args) {
        super(args, 1, "Del Train Success");
    }

    @Override
    void preprocess() throws CTSException {
        trainSerial = args[0];
        if(!Train.isSerial(trainSerial))
            throw new CTSException(ExTrain.trainSerial);
        if(!trainSystem.isTrainSerialExist(trainSerial))
            throw new CTSException(ExTrain.trainNoExist);
        train = trainSystem.getTrainBySerial(trainSerial);
    }
    @Override
    public void run() throws CTSException {
        train.deleteSelf();
    }
}
class checkTicket extends UserCommand implements ToTrain {
    String serial;
    Station from;
    Station to;
    String seat;
    Train train;
    Line line;
    public checkTicket(String[] args) {
        super(args, 4);
    }

    @Override
    void preprocess() throws CTSException {
        serial = args[0];
        seat = args[3];
        if(!Train.isSerial(serial))
            throw new CTSException(ExTrain.trainSerial);
        if(!trainSystem.isTrainSerialExist(serial))
            throw new CTSException(ExTrain.trainSerialNoExist);
        train = trainSystem.getTrainBySerial(serial);
        line = train.getMyLine();
        if(!line.isStationNameExist(args[1]) || !line.isStationNameExist(args[2]))
            throw new CTSException(ExLine.stationNoExist);
        if(!train.isSeatExist(seat)){
            throw new CTSException(ExTrain.noHaveSeat);
        }
        from = line.getStationByName(args[1]);
        to = line.getStationByName(args[2]);
    }

    @Override
    public void run() throws CTSException {
        setFinalMessage(String.format("[%s: %s->%s] seat:%s remain:%d distance:%d price:%.2f",
                train.getSerial(), from.getName(), to.getName(),
                seat, train.getRemainSeatNum(seat), to.getDistance() - from.getDistance(),
                (to.getDistance() - from.getDistance()) * train.getSeatPrice(seat)));
    }
}
class listTrain extends Command implements ToLine{
    Collection<Train> trains;
    public listTrain(String[] args) {
        super(args);
    }

    @Override
    void preprocess() throws CTSException {
        if(args.length > 1) throw new CTSException(ExOther.argumentIllegal);
    }

    @Override
    public void run() throws CTSException {
        if(args.length == 0){
            trains = TrainSystem.getAllTrain();
        }
        else{
            if(!lineSystem.isLineIdExist(args[0]))
                throw new CTSException(ExLine.lineNoExist);
            trains = lineSystem.getLineById(args[0]).getAllTrain();
        }
        int i = 1;
        for(Train train : trains){
            System.out.println("[" + i++ + "] " + train);
        }
        if(trains.size() == 0){
            System.out.println("No Trains");
        }
    }
}

class login extends Command implements ToSystem, ToUser {
    String aadhaar;
    String name;
    User user;
    public login(String[] args) {
        super(args, 2, "Login success");
    }

    @Override
    void preprocess() throws CTSException {
        aadhaar = args[0];
        name = args[1];
        if(system.isLogin())
            throw new CTSException(ExUser.alreadyLogin);
        if(!User.isAadhaar(aadhaar) || !userSystem.aadhaarIsRegister(aadhaar))
            throw new CTSException(ExUser.userNotExist);
        user = userSystem.getUserByAadhaar(aadhaar);
        if(!user.getName().equals(name))
            throw new CTSException(ExUser.wrongName);
    }

    @Override
    void run() throws CTSException {
        system.login(aadhaar);
    }
}

class logout extends Command implements ToSystem {
    public logout(String[] args) {
        super(args, 0, "Logout success");
    }

    @Override
    void preprocess() throws CTSException {
        if(!system.isLogin())
            throw new CTSException(ExUser.noLoggedIn);
    }

    @Override
    void run() throws CTSException {
        system.logout();
    }
}

class buyTicket extends LoginCommand implements ToTrain {
    String serial;
    Station from;
    Station to;
    String seat;
    int num;
    Train train;
    public buyTicket(String[] args){
        super(args, 5, "Thanks for your order");
    }

    @Override
    void preprocess() throws CTSException {
        serial = args[0];
        seat = args[3];
        if(!trainSystem.isTrainSerialExist(serial))
            throw new CTSException(ExTrain.trainNoExist);
        train = trainSystem.getTrainBySerial(serial);
        if(!train.getMyLine().isStationNameExist(args[1]) || !train.getMyLine().isStationNameExist(args[2]))
            throw new CTSException(ExLine.stationNoExist);
        from = train.getMyLine().getStationByName(args[1]);
        to = train.getMyLine().getStationByName(args[2]);
        if(!train.isSeatExist(seat))
            throw new CTSException(ExTrain.noHaveSeat);
        try{
            num = Integer.parseInt(args[4]);
        }
        catch (NumberFormatException e){
            throw new CTSException(ExTrain.ticketNum);
        }
        if(num <= 0)
            throw new CTSException(ExTrain.ticketNum);
        if(train.getRemainSeatNum(seat) < num)
            throw new CTSException(ExTrain.ticketNotEnough);
    }

    @Override
    void run() throws CTSException {
        new Order.makeNewOrder(system.getNowUser()).from(from).to(to).train(train).seat(seat).num(num).done();

    }
}

class listOrder extends LoginCommand {
    public listOrder(String[] args) {
        super(args, 0);
    }
    @Override
    void run() throws CTSException {
        system.getNowUser().listOrder();
    }
}





