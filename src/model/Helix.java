package model;

public class Helix extends SecondaryStructure {

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
        super.setType("HELIX");
        this.serNum = serNum;
        this.length = length;
    }

    String getStructureDescription() {
        return "HELIX" + serNum;
    }
}
