package User;

import CTSException.DebugException;

public class Student extends User{

    public Student(String name, String gender, String aadhaar) throws DebugException {
        super(name, gender, aadhaar);
    }
    private int remainPrivilege;

    @Override
    public String toString() {
        return super.toString() + "\nDiscount:" + remainPrivilege;
    }
}
