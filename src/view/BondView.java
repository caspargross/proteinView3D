package view;

import javafx.scene.paint.Color;
import model.ProteinEdge;
import javafx.scene.Group;

/**
 * Created by Caspar on 23.12.2016.
 */
public class BondView extends Group{

    final double RADIUS_FACTOR = 0.1;
    ProteinEdge proteinEdge;
    public MyLine3D line;


    public BondView(ProteinEdge proteinEdge) {
        this.proteinEdge = proteinEdge;
        line = new MyLine3D(
                proteinEdge.getSourceNode().getX(),
                proteinEdge.getSourceNode().getY(),
                proteinEdge.getSourceNode().getZ(),
                proteinEdge.getTargetNode().getX(),
                proteinEdge.getTargetNode().getY(),
                proteinEdge.getTargetNode().getZ(),
                Color.LIGHTBLUE);
        getChildren().add(line);

    }
}
