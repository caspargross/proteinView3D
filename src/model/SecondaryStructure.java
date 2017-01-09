package model;

/**
 * Created by Caspar on 08.01.2017.
 */
public class SecondaryStructure {

    String type;
    int serNum;             // HELIX:Serial number SHEET: strand number
    String initResName;     // Name of the initial residue
    char initChainID;       // Chain identifier start
    int initSeqNum;         // Sequence number of the inital resiude
    String endResName;      // Name of the terminal residue
    char endChainID;        // Chain identifier end
    int endSeqNum;          // Sequence number of the terminal residue
    int length;             // Length of sec. structure
    String sheetID;         // Sheet identifier
    Integer sense;          // Sheet: Sense 0: Start, -1 Antiparallel, 1 Parallel, 2: Helix

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
}
