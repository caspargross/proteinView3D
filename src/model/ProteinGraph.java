package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Caspar on 23.12.2016.
 */
public class ProteinGraph {

    public ObservableList<ProteinNode> nodeList=FXCollections.observableArrayList();
    public ObservableList<ProteinNode> nodeListFull=FXCollections.observableArrayList();
    public ObservableList<ProteinNode> selectedNodes=FXCollections.observableArrayList();
    public ObservableList<ProteinEdge> edgeList=FXCollections.observableArrayList();


    public ProteinGraph() {

        System.out.println("New Protein Graph created");
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
        }
        if (newNode.getName().equals("O")){
            nodeList.add(newNode);
        }
        if (newNode.getName().equals("CA")){
            nodeList.add(newNode);
        }
        if (newNode.getName().equals("CB")){
            nodeList.add(newNode);
        }
        if (newNode.getName().equals("C")){
            nodeList.add(newNode);
        }

        System.out.println(newNode.getName());
        nodeListFull.add(newNode);
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
            // Handle exception when no CB exists?
            if (currentAtom.getName().equals("CA")) {
                addBond(currentAtom, nextAtom("CB", i), "CA-CB");
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
        for (ProteinNode node:nodeList){
            sequenceInfo += node.getInfo();
            sequenceInfo += "\n";
        }
        return  sequenceInfo;
    }
}
