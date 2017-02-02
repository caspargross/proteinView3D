package view;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.ProteinGraph;
import model.ProteinNode;
import model.SecondaryStructure;

/**
 * Created by Caspar on 07.01.2017.
 */
public class SequenceView extends VBox {

    ProteinView proteinView;
    ProteinGraph proteinGraph;

    public SequenceView(ProteinView proteinView) {
        this.proteinView = proteinView;
        this.proteinGraph = proteinView.proteinGraph;
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
    }


    public class SequenceRow extends HBox{

        String rowString;
        boolean isSelected = false;

        public SequenceRow(ProteinNode pN) {
            //this.pN = proteinNode;
            rowString = String.format("%1$4s %2$3s %3$4s", pN.getResSeq(), pN.getName(), pN.getResName());
            Text desc = new Text(rowString);
            desc.setFont(Font.font("Monospaced"));
            Text sSdesc = new Text("");

            for (SecondaryStructure sS : proteinGraph.secondaryStructureList) {
                if (sS.getNodesInside().contains(pN)){
                    sSdesc.setText(sS.getType());
                }
            }

            setOnMouseClicked(me ->{
                if (!isSelected) {
                    setStyle("-fx-background-color: red;");
                    isSelected = true;
                    proteinGraph.nodeListSelected.add(pN);
                } else {
                    setStyle("-fx-background-color: grey;");
                    isSelected = false;
                    proteinGraph.nodeListSelected.remove(pN);
                }

            });
            getChildren().addAll(desc, sSdesc);
        }
    }

}
