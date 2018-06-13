package model;

public class Sheet extends SecondaryStructure {

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
        super.setType("SHEET");
        this.strand = strand;
        this.sheetID = sheetID;
        this.sense = sense;
    }
    String getStructureDescription() {
        return "SHEET" + sheetID;
    }
}
