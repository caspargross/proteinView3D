package view;

import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import model.ProteinEdge;
import model.ProteinGraph;
import model.ProteinNode;


/**
 * Created by Caspar on 23.12.2016.
 */
public class ProteinView extends Group {

    Group atomViewGroup;
    Group bondViewGroup;
    Group bondConnectionViewGroup;
    ProteinGraph proteinGraph;


    public ProteinView(ProteinGraph proteinGraph){

        this.proteinGraph = proteinGraph;
        this.atomViewGroup = new Group();
        this.bondViewGroup = new Group();
        this.bondConnectionViewGroup = new Group();

        // Add changelistener for proteinGraph Nodes (Atoms)
        proteinGraph.nodeList.addListener((ListChangeListener<ProteinNode>) c -> {
           while (c.next()){
               if (c.wasAdded()){
                   for (ProteinNode proteinNode:c.getAddedSubList()){
                       System.out.println("Detected Change: Added atom");
                       addAtomView(proteinNode);
                       addBondConnectionView(proteinNode);
                   }
               }
           }
        });

        // Add listener for atom selection
        proteinGraph.selectedNodes.addListener((ListChangeListener<ProteinNode>) c -> {
            while (c.next()){
                if (c.wasAdded()){
                    for (ProteinNode proteinNode:c.getAddedSubList()){
                        System.err.println("Node was selected");
                        findAtomViewFor(proteinNode).sphere.setRadius(10);
                    }
                }

                if (c.wasRemoved()){
                    for (ProteinNode proteinNode:c.getRemoved()){
                        System.err.println("Node was removed");
                        findAtomViewFor(proteinNode).sphere.setRadius(1);
                    }
                }
            }
        });

        // Add changeListener for proteinGraph Edge (Bonds)
        proteinGraph.edgeList.addListener((ListChangeListener<ProteinEdge>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(this::addBondView);
                }
            }
        });

        getChildren().add(bondConnectionViewGroup);
        getChildren().add(bondViewGroup);
        getChildren().add(atomViewGroup);

        System.out.println("Created new ProteinView");

    }

    private void addAtomView(ProteinNode proteinNode){

        AtomView atomView = new AtomView(proteinNode);
        atomViewGroup.getChildren().add(atomView);
        System.out.println("Atom View to AtomViewGroup" + atomViewGroup.getChildren().size());

    }

    private void addBondConnectionView(ProteinNode proteinNode){

        BondConnectionView bondConnectionView = new BondConnectionView(proteinNode);
        bondConnectionViewGroup.getChildren().add(bondConnectionView);

    }

    public void addBondView (ProteinEdge proteinEdge){

        BondView bondView = new BondView(proteinEdge);
        bondViewGroup.getChildren().add(bondView);
    }

    public void increaseAtomRadius(){

        for (int i = 0; i < atomViewGroup.getChildren().size(); i++) {
            AtomView atomView = (AtomView) atomViewGroup.getChildren().get(i);
            atomView.sphere.setRadius(atomView.sphere.getRadius()+atomView.RADIUS_FACTOR);
        }
    }

    public void decreaseAtomRadius(){

        for (int i = 0; i < atomViewGroup.getChildren().size(); i++) {
            AtomView atomView = (AtomView) atomViewGroup.getChildren().get(i);
            atomView.sphere.setRadius(atomView.sphere.getRadius()-atomView.RADIUS_FACTOR);
        }
    }

    public void increaseBondRadius(){

        for (int i = 0; i < bondViewGroup.getChildren().size(); i++) {
            BondView bondView = (BondView) bondViewGroup.getChildren().get(i);
            bondView.line.cylinder.setRadius(bondView.line.cylinder.getRadius()+bondView.RADIUS_FACTOR);
        }
        for (int i = 0; i < bondConnectionViewGroup.getChildren().size(); i++) {
            BondConnectionView  bondConnectionView = (BondConnectionView) bondConnectionViewGroup.getChildren().get(i);
            bondConnectionView.sphere.setRadius(bondConnectionView.sphere.getRadius()+bondConnectionView.RADIUS_FACTOR);
        }
    }

    public void decreaseBondRadius(){

        for (int i = 0; i < bondViewGroup.getChildren().size(); i++) {
            BondView bondView = (BondView) bondViewGroup.getChildren().get(i);
            bondView.line.cylinder.setRadius(Math.max(0.02,bondView.line.cylinder.getRadius()-bondView.RADIUS_FACTOR));
        }
        for (int i = 0; i < bondConnectionViewGroup.getChildren().size(); i++) {
            BondConnectionView  bondConnectionView = (BondConnectionView) bondConnectionViewGroup.getChildren().get(i);
            bondConnectionView.sphere.setRadius(Math.max(0.02,bondConnectionView.sphere.getRadius()-bondConnectionView.RADIUS_FACTOR));
        }
    }

    public AtomView findAtomViewFor(ProteinNode proteinNode){
        for (int i = 0; i < atomViewGroup.getChildren().size(); i++) {
            AtomView currentAtomView = (AtomView) atomViewGroup.getChildren().get(i);
            if (currentAtomView.proteinNode == proteinNode) return currentAtomView;
        }
        return null;
    }

}
