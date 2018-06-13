package model;

/**
 * Created by Caspar on 23.12.2016.
 */
public class ProteinEdge {

    ProteinNode sourceNode;
    ProteinNode targetNode;
    String bondDescription;

    public ProteinEdge(ProteinNode sourceNode, ProteinNode targetNode, String bondDescription) {
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.bondDescription = bondDescription;
    }

    public ProteinNode getSourceNode() {
        return sourceNode;
    }

    public ProteinNode getTargetNode() {
        return targetNode;
    }
}
