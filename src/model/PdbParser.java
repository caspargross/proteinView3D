package model;

import java.io.*;

/**
 * Created by Caspar on 23.12.2016.
 */
public class PdbParser {

    File pdbFile;
    FileReader fileReader;
    BufferedReader bufferedReader;
    ProteinGraph newProteinGraph;


    public PdbParser(File pdbFile, ProteinGraph proteinGraph) {

        this.pdbFile = pdbFile;
        try {
            fileReader = new FileReader(pdbFile);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            System.err.printf("Error in File path");
        }

        this.newProteinGraph = proteinGraph;

        // Clear all elements from the Graph
        proteinGraph.removeElements();

        // Add new Lines from the file
        readLines();
        System.out.println("PDB Parser finshed");

    }

    private void readLines() {
        try {
            String line = bufferedReader.readLine();
            String startString="4";
            while (line != null){

                if (line.startsWith("ATOM")){
                    newProteinGraph.addAtom(
                            Integer.parseInt(line.substring(7,11).replaceAll(" ", "")),   // Atom serial number
                            line.substring(13,15).replace(" ",""),  // Atom name
                            line.substring(15).charAt(0),      // + altLoc
                            line.substring(17,20).replaceAll(" ",""),  // Residue name
                            line.substring(22).charAt(0),     // Chain Identifier
                            Integer.parseInt(line.substring(22,26).replaceAll(" ", "")),  // Residue sequence number
                            Double.parseDouble(line.substring(31,38).replaceAll(" ", "")),  // xCoodinates
                            Double.parseDouble(line.substring(39,46).replaceAll(" ", "")),  // yCoordinates
                            Double.parseDouble(line.substring(47,54).replaceAll(" ", "")),  // zCoordinates
                            line.substring(77,78)  // Element symbol
                    );
                    System.out.println("Read atom");

                }

                if (line.startsWith("HETATM")){
                    newProteinGraph.addHetAtom();
                }

                line = bufferedReader.readLine();

            }

        } catch (IOException e) {
            System.err.println("Error in File Reading");
        }

    }

    public ProteinGraph getProteinGraph(){
        return newProteinGraph;
    }

}
