package view;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.ProteinGraph;
import model.ProteinNode;

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
        ProteinNode pN;
        String rowString;
        //Text name = new Text();
        //Text aminoAcid = new Text();
        //Text secondaryStructure = new Text();
        boolean isSelected = false;

        public SequenceRow(ProteinNode proteinNode) {
            this.pN = proteinNode;
            rowString = String.format("%1$4s %2$3s %3$4s %4$4s",pN.getResSeq(), pN.getName(), pN.getResName(), pN.getSecondaryStructure() );
            Text desc = new Text(rowString);
            desc.setFont(Font.font("Monospaced"));
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
            getChildren().addAll(desc);
        }
    }

}
