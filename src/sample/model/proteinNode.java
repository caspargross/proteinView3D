package sample.model;

/**
 * Created by Caspar on 23.12.2016.
 */
public class proteinNode {

    int id;

    double xCoords;
    double yCoords;
    double zCoords;
    double angle;

    String aminoAcid;
    String atomDescription;
    int subString;
    char element;

    public proteinNode(int id, double xCoords, double yCoords, double zCoords, double angle,
                       String aminoAcid, String atomDescription, int subString, char element) {
        this.id = id;
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.zCoords = zCoords;
        this.angle = angle;
        this.aminoAcid = aminoAcid;
        this.atomDescription = atomDescription;
        this.subString = subString;
        this.element = element;
    }


}
