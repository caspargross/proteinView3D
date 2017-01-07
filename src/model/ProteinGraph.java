package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Caspar on 23.12.2016.
 */
public class ProteinGraph {

    public ObservableList<ProteinNode> nodeList=FXCollections.observableArrayList();
    public ObservableList<ProteinEdge> edgeList=FXCollections.observableArrayList();

    public ProteinGraph() {

        System.out.println("New Protein Graph created");

    }

    public void addAtom(int serial, String name, Character altLoc, String resName, Character chainID,
                        int resSeq, double x, double y, double z, String element){
        ProteinNode newNode = new ProteinNode(serial, name, altLoc, resName, chainID,
        resSeq, x, y, z, element);
        nodeList.add(newNode);

    }

    public void addHetAtom(){

    }

    public void removeElements(){
        nodeList.removeAll();
    }
}
