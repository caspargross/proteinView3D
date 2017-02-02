package view;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.SelectionModel;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.MySelectionModel;
import model.ProteinGraph;
import model.ProteinNode;

/**
 * Created by Caspar on 07.01.2017.
 */
public class SequenceView extends VBox {

    ProteinView proteinView;
    ProteinGraph model;
    MySelectionModel<ProteinNode> selectionModel;

    static Background selectedBG = new Background(new BackgroundFill(Color.ROYALBLUE, new CornerRadii(2), new Insets(0)));
    static Background unSelectedBG = new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(2), new Insets(0)));

    public SequenceView(ProteinView proteinView, MySelectionModel sm) {
        this.proteinView = proteinView;
        this.model = proteinView.proteinGraph;
        this.selectionModel = sm;
        setupListeners();

    }

    private void setupListeners(){
                proteinView.atomViewGroup.getChildren().addListener((ListChangeListener<? super Node>) c -> {
            while (c.next()) {
                if (c.wasAdded() || c.wasUpdated()) {
                    for (Node node:c.getAddedSubList()){
                        AtomView atomView = (AtomView) node;
                        SequenceRow newRow = new SequenceRow(atomView.proteinNode);
                        getChildren().add(newRow);
                    }
                }
            }
        });

        proteinView.secondaryStructureViewGroup.getChildren().addListener((ListChangeListener<? super Node>) c->{
            while (c.next()) {
                if (c.wasAdded() || c.wasUpdated()) {
                    for (Node node:c.getAddedSubList()){
                        SecondaryStructureView sSView = (SecondaryStructureView) node;
                        for (AtomView atomView : sSView.atomViewList){
                            for (int i = 0; i < getChildren().size(); i++) {
                                SequenceRow sR = (SequenceRow) getChildren().get(i);
                                if (atomView.proteinNode.equals(sR.pN)) {
                                    ImageView img = null;
                                    if (sSView.type.equals("HELIX")) {

                                        if (sR.pN.equals(sSView.startAtom.proteinNode)) {
                                            img = new ImageView("img/HelixTop.png");
                                        } else if (sR.pN.equals(sSView.endAtom.proteinNode)) {
                                            img = new ImageView("img/HelixBot.png");
                                        } else {
                                            img = new ImageView("img/HelixMid.png");
                                        }

                                    }

                                    if (sSView.type.equals("SHEET")){

                                        if (sR.pN.equals(sSView.startAtom.proteinNode)) {
                                            img = new ImageView("img/SheetTop.png");
                                        } else if (sR.pN.equals(sSView.endAtom.proteinNode)) {
                                            img = new ImageView("img/SheetBot.png");
                                        } else {
                                            img = new ImageView("img/SheetMid.png");
                                        }
                                    }
                                    sR.getChildren().add(img);
                                }
                            }

                        }
                    }
                }
            }
        });

        selectionModel.getSelectedIndices().addListener((ListChangeListener<? super Integer>) c ->{
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(i -> {
                        SequenceRow sR = (SequenceRow) getChildren().get(i);
                        sR.setBackground(selectedBG);
                    });
                }

                if (c.wasRemoved()){
                    c.getRemoved().forEach(i -> {
                        SequenceRow sR = (SequenceRow) getChildren().get(i);
                        sR.setBackground(unSelectedBG);
                    });
                }
            }
        });


    }


    public class SequenceRow extends HBox{

        String rowString;
        ProteinNode pN;


        public SequenceRow(ProteinNode proteinNode) {
            this.pN = proteinNode;
            rowString = String.format("%1$4s %2$3s %3$4s  ", pN.getResSeq(), pN.getName(), pN.getResName());
            Text desc = new Text(rowString);
            desc.setFont(Font.font("Monospaced", 14));


            setOnMouseClicked(me ->{
                System.out.println("Mouse clicked on sequence Row");
                selectionModel.toggleSelect(pN);
            });

            //backgroundProperty().bind(selectionModel.selectedItemProperty(pN));
            //selectionModel.selectedItemProperty()
            setHeight(20);
            getChildren().addAll(desc);


        }




    }

}
