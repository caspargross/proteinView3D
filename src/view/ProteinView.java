package view;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.MouseButton;
import javafx.scene.Node;
import javafx.scene.shape.MeshView;
import model.*;

import java.util.ArrayList;


/**
 * Created by Caspar on 23.12.2016.
 */

public class ProteinView extends Group {

    public Group atomViewGroup;
    private Group bondViewGroup;
    private Group bondConnectionViewGroup;
    private Group ribbonViewGroup;
    public Group secondaryStructureViewGroup;
    public MySelectionModel<ProteinNode> selectionModel;
    public Property<String> atomInfo = new SimpleStringProperty("");

    ProteinGraph proteinGraph;

    public Property<Boolean> showAtomsProperty = new SimpleBooleanProperty(Boolean.TRUE);
    public Property<Boolean> showBondsProperty = new SimpleBooleanProperty(Boolean.TRUE);
    public Property<Boolean> showRibbonsProperty = new SimpleBooleanProperty(Boolean.FALSE);
    public Property<Boolean> showSecondaryStructureProperty = new SimpleBooleanProperty(Boolean.FALSE);
    public DoubleProperty atomSizeProperty = new SimpleDoubleProperty(6);
    public DoubleProperty bondSizeProperty = new SimpleDoubleProperty(3);


    public ProteinView(ProteinGraph proteinGraph, MySelectionModel<ProteinNode>sM){

        this.selectionModel = sM;
        this.proteinGraph = proteinGraph;
        this.atomViewGroup = new Group();
        this.bondViewGroup = new Group();
        this.bondConnectionViewGroup = new Group();
        this.ribbonViewGroup = new Group();
        this.secondaryStructureViewGroup = new Group();



        // Add changelistener for model Nodes (Atoms)
        proteinGraph.nodeList.addListener((ListChangeListener<ProteinNode>) c -> {
           while (c.next()){
               if (c.wasAdded()){
                   for (ProteinNode proteinNode:c.getAddedSubList()){
                       //System.out.println("Detected Change: Added atom");
                       createAtomView(proteinNode);
                       //createBondConnectionView(proteinNode);
                   }
               }
               if (c.wasRemoved()){
                   for (ProteinNode proteinNode:c.getRemoved()){
                       atomViewGroup.getChildren().remove(findAtomViewFor(proteinNode));
                   }
               }
           }
        });


        // Add changeListener for model Edge (Bonds)
        proteinGraph.edgeList.addListener((ListChangeListener<ProteinEdge>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(this::createBondView);
                }
                if (c.wasRemoved()){
                    for (ProteinEdge proteinEdge:c.getRemoved()){
                        bondViewGroup.getChildren().remove(0, bondViewGroup.getChildren().size());
                        bondConnectionViewGroup.getChildren().remove(0, bondConnectionViewGroup.getChildren().size());
                    }
                }
            }
        });


        // Add changeListener for secondaryStructure elements in Model
        proteinGraph.pdbFullyRead.addListener((observable, oldValue, newValue) -> {
            for (SecondaryStructure sS:proteinGraph.secondaryStructureList) {
                // Start adding the secondary Structure Views
                createSecondaryStructureView(sS);
                // Copy ProteinNodes into the selectionModel
                initializeSelectionModel();

            }
            // Create RibbonView
            RibbonView newRibbonView = new RibbonView(this);
            ribbonViewGroup.getChildren().addAll(newRibbonView.getChildren());
            System.out.println("RIBBONVIEWGROUP ADDED");

        });


        // Listeners to indicate which elements are shown in the View
        showAtomsProperty.addListener((observable, oldValue, newValue) -> {
            for (int i = 0; i < atomViewGroup.getChildren().size(); i++) {
                AtomView atomView = (AtomView) atomViewGroup.getChildren().get(i);
                atomView.setVisible(newValue);
            }

        });
        showBondsProperty.addListener((observable, oldValue, newValue) -> {
            for (int i = 0; i < bondViewGroup.getChildren().size(); i++) {
                BondView bondView = (BondView) bondViewGroup.getChildren().get(i);
                bondView.setVisible(newValue);
            }
            for (int i = 0; i < bondConnectionViewGroup.getChildren().size(); i++) {
                BondConnectionView  bondConnectionView = (BondConnectionView) bondConnectionViewGroup.getChildren().get(i);
                bondConnectionView.setVisible(newValue);
            }

        });
        showRibbonsProperty.addListener((observable, oldValue, newValue) -> {
            for (int i = 0; i < ribbonViewGroup.getChildren().size(); i++) {
                MeshView meshView = (MeshView) ribbonViewGroup.getChildren().get(i);
                meshView.setVisible(newValue);
            }

        });

        showSecondaryStructureProperty.addListener((observable, oldValue, newValue) -> {
            for (int i = 0; i < secondaryStructureViewGroup.getChildren().size(); i++) {
                SecondaryStructureView sSView = (SecondaryStructureView) secondaryStructureViewGroup.getChildren().get(i);
                sSView.setVisible(newValue);
            }

        });

        // Add listeners for bond and atom size
        atomSizeProperty.addListener((observable, oldValue, newValue) -> {
            for (int i = 0; i < atomViewGroup.getChildren().size(); i++) {
                AtomView atomView = (AtomView) atomViewGroup.getChildren().get(i);
                atomView.sphere.setRadius((Double)newValue * atomView.RADIUS_FACTOR);
            }
        });
        bondSizeProperty.addListener((observable, oldValue, newValue) -> {
            for (int i = 0; i < bondViewGroup.getChildren().size(); i++) {
                BondView bondView = (BondView) bondViewGroup.getChildren().get(i);
                bondView.line.cylinder.setRadius((Double) newValue *bondView.RADIUS_FACTOR);
            }
            for (int i = 0; i < bondConnectionViewGroup.getChildren().size(); i++) {
                BondConnectionView  bondConnectionView = (BondConnectionView) bondConnectionViewGroup.getChildren().get(i);
                bondConnectionView.sphere.setRadius((Double) newValue *bondConnectionView.RADIUS_FACTOR);
            }
        });


        getChildren().add(bondConnectionViewGroup);
        getChildren().add(bondViewGroup);
        getChildren().add(atomViewGroup);
        getChildren().add(secondaryStructureViewGroup);
        getChildren().add(ribbonViewGroup);

        System.out.println("Created new ProteinView");


    }

    private void addMouseEvents(AtomView atomView){
        atomView.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)){
                selectionModel.toggleSelect(atomView.proteinNode);
            }
        });

        atomView.setOnMouseEntered(e -> {
            atomInfo.setValue(atomView.proteinNode.getResSeq()+ " " +
                    atomView.proteinNode.getName() + " " + atomView.proteinNode.getResName());
        });

        atomView.setOnMouseExited(e -> {
            atomInfo.setValue("");
        });

    }

    private void initializeSelectionModel(){
        ProteinNode[] proteinNodeArray = new ProteinNode[atomViewGroup.getChildren().size()];

        for (int i = 0; i < atomViewGroup.getChildren().size(); i++) {
            AtomView atomView = (AtomView) atomViewGroup.getChildren().get(i);
            proteinNodeArray[i] = atomView.proteinNode;
        }
        selectionModel.setItems(proteinNodeArray);
        System.out.println("Secondary structure View Created");
        selectionModel.getItems();
    }
    private void createAtomView(ProteinNode proteinNode){

        AtomView atomView = new AtomView(proteinNode);
        addMouseEvents(atomView);
        atomViewGroup.getChildren().add(atomView);
        System.out.println("Atom View to AtomViewGroup" + atomViewGroup.getChildren().size());

    }

    private void createBondConnectionView(ProteinNode proteinNode){

        BondConnectionView bondConnectionView = new BondConnectionView(proteinNode);
        bondConnectionViewGroup.getChildren().add(bondConnectionView);

    }

    public void createBondView(ProteinEdge proteinEdge){

        BondView bondView = new BondView(proteinEdge);
        bondViewGroup.getChildren().add(bondView);
        createBondConnectionView(proteinEdge.getSourceNode());
        createBondConnectionView(proteinEdge.getTargetNode());
    }

    public void createSecondaryStructureView(SecondaryStructure sS){

        ArrayList<AtomView> atomViewsInside = new ArrayList<>();
        for (ProteinNode node : sS.getNodesInside()) {
            atomViewsInside.add(findAtomViewFor(node));
        }
        SecondaryStructureView  newSSView = new SecondaryStructureView(sS.getType(), atomViewsInside);
        secondaryStructureViewGroup.getChildren().add(newSSView);
    }



    public AtomView findAtomViewFor(ProteinNode proteinNode){
        for (int i = 0; i < atomViewGroup.getChildren().size(); i++) {
            AtomView currentAtomView = (AtomView) atomViewGroup.getChildren().get(i);
            if (currentAtomView.proteinNode == proteinNode) return currentAtomView;
        }
        return null;
    }

}
