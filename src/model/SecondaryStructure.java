package model;

/**
 * Created by Caspar on 08.01.2017.
 */

abstract class SecondaryStructure {

    String type;
    String initResName;     // Name of the initial residue
    char initChainID;       // Chain identifier start
    int initSeqNum;         // Sequence number of the inital resiude
    String endResName;      // Name of the terminal residue
    char endChainID;        // Chain identifier end
    int endSeqNum;          // Sequence number of the terminal residue

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
    }


    abstract String getStructureDescription();

    /**public SecondaryStructure(String type, int serNum, String initResName, char initChainID, int initSeqNum, String endResName, char endChainID, int endSeqNum, int length) {
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
    }**/
}

class Sheet extends SecondaryStructure {

    // Sheet specifix values
    int strand;             // Strand number (starts at 1 and increases)
    String sheetID;         // Sheet identifier
    Integer sense;          // Sheet: Sense 0: Start, -1 Antiparallel, 1 Parallel, 2: Helix

    Sheet(int strand,             // Helix strand number
          String initResName,     // Name of the initial residue
          char initChainID,       // Chain identifier start
          int initSeqNum,         // Sequence number of the inital resiude
          String endResName,      // Name of the terminal residue
          char endChainID,        // Chain identifier end
          int endSeqNum,          // Sequence number of the terminal residue
          String sheetID,
          int sense) {

        super(initResName, initChainID, initSeqNum, endResName, endChainID, endSeqNum);
        super.type = "SHEET";
        this.strand = strand;
        this.sheetID = sheetID;
        this.sense = sense;
    }
    String getStructureDescription() {
        return "SHEET" + sheetID;
    }
}

class Helix extends SecondaryStructure {

    // Helix specific values
    int serNum;
    int length;

    Helix(int serNum,             // Serial number of the helix
          String initResName,     // Name of the initial residue
          char initChainID,       // Chain identifier start
          int initSeqNum,         // Sequence number of the inital resiude
          String endResName,      // Name of the terminal residue
          char endChainID,        // Chain identifier end
          int endSeqNum,          // Sequence number of the terminal residue
          int length) {

        super(initResName, initChainID, initSeqNum, endResName, endChainID, endSeqNum);
        super.type = "HELIX";
        this.serNum = serNum;
        this.length = length;
    }

    String getStructureDescription() {
        return "HELIX" + serNum;
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