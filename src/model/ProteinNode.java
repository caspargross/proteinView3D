package model;

/**
 * Created by Caspar on 23.12.2016.
 */
public class ProteinNode {

    int serial;
    String name;
    Character altLoc;
    String resName;
    Character chainID;
    int resSeq;
    double x;
    double y;
    double z;
    String element;

    public ProteinNode(int serial, String name, Character altLoc, String resName, Character chainID,
                       int resSeq, double x, double y, double z, String element) {
        this.serial = serial;
        this.name = name;
        this.altLoc = altLoc;
        this.resName = resName;
        this.chainID = chainID;
        this.resSeq = resSeq;
        this.x = x;
        this.y = y;
        this.z = z;
        this.element = element;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getElement(){
        return element;
    }

    public String getName() {
        return name;
    }

    public String getResName(){
        return resName;
    }

    public String getInfo(){
        return (serial+" " +name+" "+chainID);
    }
}
