package Line;

import CTSException.*;
import User.UserSystem;

import java.util.Collection;
import java.util.TreeMap;

public class LineSystem {
    //单例类实现
    static private LineSystem onlyInstance = new LineSystem();
    static public LineSystem getInstance(){
        return onlyInstance;
    }

    private TreeMap<String, Line> lines = new TreeMap<String, Line>();

    public Collection<Line> getAllLine(){
        return lines.values();
    }

    public Line getLineById(String lineId) throws DebugException{
        if(!isLineIdExist(lineId))
            throw new DebugException(ExLine.lineNoExist);
        Line ret = lines.get(lineId);
        return ret;
    }

    public boolean isLineIdExist(String id){
        Line ret = lines.get(id);
        return ret != null;
    }

    public void addLine(Line line) throws DebugException {
        if(isLineIdExist(line.getId())){
            throw new DebugException(ExLine.lineExist);
        }
        lines.put(line.getId(), line);
    }
    public void deleteLine(String lineId) throws DebugException{
        if(!isLineIdExist(lineId)){
            throw new DebugException(ExLine.lineNoExist);
        }
        lines.remove(lineId);
    }

}
