package model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Caspar on 23.12.2016.
 */
public class ProteinGraph {

    // Lists observed by the View
    public ObservableList<ProteinNode> nodeList =FXCollections.observableArrayList();
    public ObservableList<ProteinEdge> edgeList=FXCollections.observableArrayList();
    public ObservableList<ProteinNode> nodeListSelected =FXCollections.observableArrayList();
    public ObservableList<SecondaryStructure> secondaryStructureList=FXCollections.observableArrayList();

    // Lists containing all nodes and edges
    public ObservableList<ProteinNode> nodeListBackbone =FXCollections.observableArrayList();
    public ObservableList<ProteinNode> nodeListResidue =FXCollections.observableArrayList();
    public ObservableList<ProteinNode> nodeListSidechain =FXCollections.observableArrayList();
    public ObservableList<ProteinNode> nodeListFull=FXCollections.observableArrayList();

    public ObservableList<ProteinEdge> edgeListBackbone=FXCollections.observableArrayList();
    public ObservableList<ProteinEdge> edgeListResidue=FXCollections.observableArrayList();
    public ObservableList<ProteinEdge> edgeListSidechain=FXCollections.observableArrayList();
    public ObservableList<ProteinEdge> edgeListFull=FXCollections.observableArrayList();

    // String with Amino Acid sequence (single letter code)
    private String aminoAcidSequence;


    public Property<Boolean> pdbFullyRead= new SimpleBooleanProperty(Boolean.FALSE);
    public StringProperty header = new SimpleStringProperty("");


    public ProteinGraph() {

        System.out.println("New Protein Graph created");
        aminoAcidSequence = "";
    }

    public void addAtom(
            int serial,
            String name,
            Character altLoc,
            String resName,
            Character chainID,
            int resSeq,
            double x,
            double y,
            double z,
            String element){

        ProteinNode newNode = new ProteinNode(
                serial,
                name,
                altLoc,
                resName,
                chainID,
                resSeq,
                x, y, z,
                element);

        if (newNode.getName().equals("N")){
            nodeList.add(newNode);
            aminoAcidSequence += AminoAcidTools.transFormCode(newNode.getResName());
            assignAtomToStructure(newNode);
        }
        if (newNode.getName().equals("O")){
            nodeList.add(newNode);
            assignAtomToStructure(newNode);
        }
        if (newNode.getName().equals("C")){
            nodeList.add(newNode);
            assignAtomToStructure(newNode);
        }
        if (newNode.getName().equals("CA")){
            nodeList.add(newNode);
            assignAtomToStructure(newNode);
        }
        if (newNode.getName().equals("CB")){
            nodeList.add(newNode);
            assignAtomToStructure(newNode);
        }

        System.out.println(newNode.getName());
        nodeListFull.add(newNode);
    }

    public void addSecondaryStructureHelix(
            String type,
            int serNum,             // Serial number of the helix
            String initResName,     // Name of the initial residue
            char initChainID,       // Chain identifier start
            int initSeqNum,         // Sequence number of the inital resiude
            String endResName,      // Name of the terminal residue
            char endChainID,        // Chain identifier end
            int endSeqNum,          // Sequence number of the terminal residue
            int length){
        SecondaryStructure newHelix = new Helix(
                serNum,
                initResName,
                initChainID,
                initSeqNum,
                endResName,
                endChainID,
                endSeqNum,
                length);

        secondaryStructureList.add(newHelix);
        System.err.println("Secondary Structure: Helix");

    }

    public void addSecondaryStructureSheet(
            String type,
            int strand,             // Serial number of the helix
            String initResName,     // Name of the initial residue
            char initChainID,       // Chain identifier start
            int initSeqNum,         // Sequence number of the inital resiude
            String endResName,      // Name of the terminal residue
            char endChainID,        // Chain identifier end
            int endSeqNum,          // Sequence number of the terminal residue
            String sheetID,
            int sense){
        SecondaryStructure newSheet = new Sheet(
                strand,
                initResName,
                initChainID,
                initSeqNum,
                endResName,
                endChainID,
                endSeqNum,
                sheetID,
                sense
                );
        secondaryStructureList.add(newSheet);
        System.err.println("Secondary Structure: Sheet");

    }

    public void addHeader(String header){
        this.header.setValue(header);
    }

    public void assignBonds(){

        for (int i = 0; i < nodeListFull.size()-1; i++) {
            System.out.println("Quering atom" +i);
            ProteinNode currentAtom = nodeListFull.get(i);

            if (currentAtom.getName().equals("N")) {
                addBond(currentAtom, nextAtom("CA", i), "N-CA");
            }
            if (currentAtom.getName().equals("CA")) {
                addBond(currentAtom, nextAtom("C", i), "CA-C");
            }

            //Dont create Ca-Cb bond for Glycine
            if (!currentAtom.getResName().equals("GLY")) {
                if (currentAtom.getName().equals("CA")) {
                    addBond(currentAtom, nextAtom("CB", i), "CA-CB");
                }
            }

            if (currentAtom.getName().equals("C")) {
                addBond(currentAtom, nextAtom("O", i), "C-O");
            }
            if (currentAtom.getName().equals("C")) {
                addBond(currentAtom, nextAtom("N", i), "C-N");
            }
        }
    }

    public void addBond(ProteinNode sourceAtom, ProteinNode targetAtom, String bondDescription){
        // Check if end of protein is reached
        if (sourceAtom != targetAtom) {
            ProteinEdge newBond = new ProteinEdge(sourceAtom, targetAtom, bondDescription);
            edgeList.add(newBond);
            System.out.println("Bond added");
        }
    }

    // Check for every atom if the resSeq is inside a secondary Structure
    public void assignAtomToStructure(ProteinNode proteinNode){

        for (SecondaryStructure sS : secondaryStructureList) {
            if (proteinNode.getResSeq() >= sS.getInitSeqNum() && proteinNode.getResSeq() <= sS.getEndSeqNum()) {
                sS.addNode(proteinNode);
                System.out.println("Added Node to secondary Structure");
            }
        }
    }

    public ProteinNode nextAtom(String name, int startPosition){

        int currentposition = startPosition;
        ProteinNode nextNode = nodeListFull.get(currentposition);
        while (!nextNode.name.equals(name)){
            // End of chain reached
            if (currentposition == nodeListFull.size()-1){
                return nodeListFull.get(startPosition);
            }
            currentposition++;
            nextNode = nodeListFull.get(currentposition);
            // Return original node when end of protein is reached.
            if (nextNode == null){
                return nodeListFull.get(startPosition);
            }
        }
        return nextNode;
    }

    public void addHetAtom(){

    }

    public void removeElements(){

        nodeList.removeAll();
    }

    public String getSequenceInfo(){

        String sequenceInfo ="";
        for (ProteinNode node: nodeList){
            sequenceInfo += node.getInfo();
            sequenceInfo += "\n";
        }
        return  sequenceInfo;
    }


    public String getAminoAcidSequence() {return  aminoAcidSequence;}


}
