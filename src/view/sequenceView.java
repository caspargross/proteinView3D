package view;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.ProteinGraph;
import model.ProteinNode;

/**
 * Created by Caspar on 07.01.2017.
 */
public class SequenceView extends VBox {

    ProteinGraph proteinGraph;

    public SequenceView(ProteinGraph proteinGraph) {
        this.proteinGraph = proteinGraph;
        setupListeners();
    }

    private void setupListeners(){
        proteinGraph.nodeList.addListener((ListChangeListener<ProteinNode>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (ProteinNode proteinNode:c.getAddedSubList()){
                        System.out.println("Detected Change: Added atom");
                        SequenceRow newRow = new SequenceRow(proteinNode);
                        getChildren().add(newRow);
                    }
                }
            }
        });
    }


    public class SequenceRow extends HBox{
        ProteinNode proteinNode;
        Text name = new Text();
        Text aminoAcid = new Text();
        boolean isSelected = false;

        public SequenceRow(ProteinNode proteinNode) {
            this.proteinNode = proteinNode;
            this.name.setText(proteinNode.getName());
            this.aminoAcid.setText(proteinNode.getResName());
            setOnMouseClicked(me ->{
                if (!isSelected) {
                    setStyle("-fx-background-color: red;");
                    isSelected = true;
                    proteinGraph.selectedNodes.add(proteinNode);
                } else {
                    setStyle("-fx-background-color: grey;");
                    isSelected = false;
                    proteinGraph.selectedNodes.remove(proteinNode);
                }

            });
            getChildren().addAll(name, aminoAcid);
        }
    }

}
