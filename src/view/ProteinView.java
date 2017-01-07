package view;

import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import model.ProteinGraph;
import model.ProteinNode;
import presenter.Presenter;


/**
 * Created by Caspar on 23.12.2016.
 */
public class ProteinView extends Group {

    Group atomViewGroup;
    Group bondViewGroup;
    ProteinGraph proteinGraph;


    public ProteinView(ProteinGraph proteinGraph){

        this.proteinGraph = proteinGraph;
        this.atomViewGroup = new Group();
        this.bondViewGroup = new Group();

        // Add changelistener for proteinGraph
        proteinGraph.nodeList.addListener((ListChangeListener<ProteinNode>) c -> {
           while (c.next()) {
               if (c.wasAdded()) {
                   for (ProteinNode proteinNode:c.getAddedSubList()){
                       System.out.println("Detected Change: Added atom");
                       addAtomView(proteinNode);
                   }
               }
           }
        });

        /** // Add changelistener for atomViewGroup
        atomViewGroup.getChildren().addListener((ListChangeListener<Node>) c -> {
            while (c.next()) {
                if (c.wasAdded()){
                    for (Node node:c.getAddedSubList()){
                        getChildren().add(node);
                        System.out.println("Added atomView from AtomViewGroup to Pane");
                    }
                }
            }
        });
         **/
        getChildren().add(atomViewGroup);

        System.out.println("Created new ProteinView");

    }

    private void addAtomView(ProteinNode proteinNode){

        AtomView atomView = new AtomView(proteinNode);
        atomViewGroup.getChildren().add(atomView);
        System.out.println("Atom View to AtomViewGroup" + atomViewGroup.getChildren().size());

    }

    public void increaseAtomRadius(){

        for (int i = 0; i < atomViewGroup.getChildren().size(); i++) {
            AtomView atomView = (AtomView) atomViewGroup.getChildren().get(i);
            atomView.sphere.setRadius(atomView.sphere.getRadius()+atomView.radiusFactor);
        }
    }

    public void decreaseAtomRadius(){

        for (int i = 0; i < atomViewGroup.getChildren().size(); i++) {
            AtomView atomView = (AtomView) atomViewGroup.getChildren().get(i);
            atomView.sphere.setRadius(atomView.sphere.getRadius()-atomView.radiusFactor);
        }
    }

}
