package model;

import java.util.ArrayList;

/**
 * Created by Caspar on 08.01.2017.
 */

public abstract class SecondaryStructure {

    private String type;
    private String initResName;     // Name of the initial residue
    private char initChainID;       // Chain identifier start
    private int initSeqNum;         // Sequence number of the inital resiude
    private String endResName;      // Name of the terminal residue
    private char endChainID;        // Chain identifier end
    private int endSeqNum;          // Sequence number of the terminal residue

    ArrayList<ProteinNode> nodesInside;

    // Superclass constructor (needed for all types of secondary Structure)
    SecondaryStructure(String initResName,     // Name of the initial residue
                       char initChainID,       // Chain identifier start
                       int initSeqNum,         // Sequence number of the inital resiude
                       String endResName,      // Name of the terminal residue
                       char endChainID,        // Chain identifier end
                       int endSeqNum) {
        this.initResName = initResName;
        this.initChainID = initChainID;
        this.initSeqNum = initSeqNum;
        this.endResName = endResName;
        this.endChainID = endChainID;
        this.endSeqNum = endSeqNum;
        this.nodesInside = new ArrayList<>();
    }

    public ProteinNode getStartNode() {
        return nodesInside.get(0);
    }

    public ProteinNode getEndNode() {
        return nodesInside.get(nodesInside.size()-1);
    }

    abstract String getStructureDescription();

    public String getType() {
        return type;
    }

    public String getInitResName() {
        return initResName;
    }

    public char getInitChainID() {
        return initChainID;
    }

    public int getInitSeqNum() {
        return initSeqNum;
    }

    public String getEndResName() {
        return endResName;
    }

    public char getEndChainID() {
        return endChainID;
    }

    public int getEndSeqNum() {
        return endSeqNum;
    }

    public ArrayList<ProteinNode> getNodesInside() {
        return nodesInside;
    }

    public void addNode(ProteinNode node) {
        nodesInside.add(node);
    }

    public void setType(String str){
        this.type = str;
    }
}

/**class nullStructure extends SecondaryStructure {

    String getStructureDescription() {
        return null;
    }
} **/


/**
 // Constructor for Helix entries
 public SecondaryStructure(String type, int serNum, String initResName, char initChainID, int initSeqNum, String endResName, char endChainID, int endSeqNum, int length) {
 this.type = type;
 this.serNum = serNum;
 this.initResName = initResName;
 this.initChainID = initChainID;
 this.initSeqNum = initSeqNum;
 this.endResName = endResName;
 this.endChainID = endChainID;
 this.endSeqNum = endSeqNum;
 this.length = length;
 this.sheetID = "empty";
 this.sense = 2;
 }

 // Constructor for Sheet entries
 public SecondaryStructure(String type, int serNum, String initResName, char initChainID, int initSeqNum, String endResName, char endChainID, int endSeqNum, String sheetID, Integer sense) {
 this.type = type;
 this.serNum = serNum;
 this.initResName = initResName;
 this.initChainID = initChainID;
 this.initSeqNum = initSeqNum;
 this.endResName = endResName;
 this.endChainID = endChainID;
 this.endSeqNum = endSeqNum;
 this.length = 0;
 this.sheetID = sheetID;
 this.sense = sense;
 }
 **/