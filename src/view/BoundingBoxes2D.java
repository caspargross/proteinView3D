package view;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.Property;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.ProteinGraph;
import model.ProteinNode;

/**
 * Created by Caspar on 07.01.2017.
 */
public class BoundingBoxes2D extends Group{
    /**
    ProteinGraph proteinGraph;
    {

        this.proteinGraph = proteinGraph;

        proteinGraph.selectedNodes.addListener((ListChangeListener<ProteinNode>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (int i = 0; i < c.getAddedSize(); i++) {


                    }
                }
            }
        });

        proteinGraph.selectedNodes.addListener((ListChangeListener<ProteinNode>) c -> {
            while (c.next()){
                if (c.wasAdded()) {
                    for (int i = 0; i < c.getAddedSize(); i++) {

                        Node node = c.getAddedSubList().get(i);
                        Rectangle rect = new Rectangle();
                        properties = new Property[] {view.woldTransformProperty, node.translateXProperty(),
                                node.translateYProperty(), node.scaleXProperty(), node.scaleYProperty()};
                        final ObjectBinding<Rectangle> binding = createBoundingBoxBinding(view.topPane, node, properties);

                        // Bind Translate Properties
                        rect.xProperty().bind(new DoubleBinding() {
                            {
                                bind(binding);
                            }
                            @Override
                            protected double computeValue() {
                                return binding.get().getX();
                            }
                        });

                        rect.yProperty().bind(new DoubleBinding() {
                            {
                                bind(binding);
                            }
                            @Override
                            protected double computeValue() {
                                return binding.get().getY();
                            }
                        });
                        rectangleGroup.getChildren().add(rect);


                    }
                }
            }
        });

    }
    public BoundingBoxes2D(ProteinGraph proteinGraph)

    public ObjectBinding<Rectangle> createBoundingBoxBinding(Pane pane, Node node, Property[] properties) {

        final ObjectBinding<Rectangle> binding = new  ObjectBinding<Rectangle>(){
            @Override
            protected Rectangle computeValue() {
                final Bounds boundsOnScreen = node.localToScreen(node.getBoundsInLocal());
                final Bounds paneBoundsOnScreen = pane.localToScreen(pane.getBoundsInLocal());
                final double xInScene = boundsOnScreen.getMinX()-paneBoundsOnScreen.getMinX();
                final double yInScene = boundsOnScreen.getMinY()-paneBoundsOnScreen.getMinY();
                return new Rectangle(xInScene, yInScene, boundsOnScreen.getWidth(), boundsOnScreen.getHeight());
            }
        };

        return binding;
    }



    public void addBox(MyNodeView3D nodeView){
        System.out.println("Box added");
        Rectangle rect = new Rectangle(nodeView.getTranslateX()-1, nodeView.getTranslateY()-1, nodeView.getScaleX()+2, nodeView.getScaleY()+2);
        rectangleGroup.getChildren().add(rect);

    }
    **/
}
