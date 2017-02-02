package view;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.Property;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.NodeOrientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.MySelectionModel;
import model.ProteinGraph;
import model.ProteinNode;

/**
 * Created by Caspar on 07.01.2017.
 */
public class BoundingBoxes2D extends Group{


    Group rectangleGroup = new Group();
    AtomView atomView;
    // Selection model needed because I am unable to Pick atoms through rectangles (PickOnBounds)
    MySelectionModel<ProteinNode> selectionModel;


    public BoundingBoxes2D(Pane pane, AtomView atomView, Property transformProperty, SubScene subScene, MySelectionModel<ProteinNode> mS) {
        this.atomView = atomView;
        this.selectionModel = mS;

        Property[] properties = new Property[]{
                transformProperty,
                atomView.translateXProperty(), atomView.translateYProperty(), atomView.translateZProperty(),
                atomView.scaleXProperty(), atomView.scaleYProperty(), atomView.scaleZProperty(),
                subScene.widthProperty(), subScene.heightProperty(), atomView.sphere.radiusProperty()};

        ObjectBinding<Rectangle> binding = createBoundingBoxBinding(pane, atomView, properties);
        Rectangle rectangle = new Rectangle();

        rectangle.xProperty().bind(new DoubleBinding() {
            {
                bind(binding);
            }

            @Override
            protected double computeValue() {
                return binding.get().getX();
            }
        });

        rectangle.yProperty().bind(new DoubleBinding() {
            {
                bind(binding);
            }

            @Override
            protected double computeValue() {
                return binding.get().getY();
            }
        });

        rectangle.scaleXProperty().bind(new DoubleBinding() {
            {
                bind(binding);
            }

            @Override
            protected double computeValue() {
                return binding.get().getScaleX();
            }
        });

        rectangle.scaleYProperty().bind(new DoubleBinding() {
            {
                bind(binding);
            }

            @Override
            protected double computeValue() {
                return binding.get().getScaleY();
            }
        });

        rectangle.scaleZProperty().bind(new DoubleBinding() {
            {
                bind(binding);
            }

            @Override
            protected double computeValue() {
                return binding.get().getScaleZ();
            }
        });

        rectangle.heightProperty().bind(new DoubleBinding() {
            {
                bind(binding);
            }

            @Override
            protected double computeValue() {
                return binding.get().getHeight();
            }
        });

        rectangle.widthProperty().bind(new DoubleBinding() {
            {
                bind(binding);
            }

            @Override
            protected double computeValue() {
                return binding.get().getWidth();
            }
        });


        rectangle.setPickOnBounds(false);
        this.setPickOnBounds(false);
        getChildren().add(rectangle);

        rectangle.setStroke(Color.ROYALBLUE);
        rectangle.setStrokeWidth(2);
        rectangle.setFill(Color.TRANSPARENT);

        setOnMouseClicked(me ->{
            System.out.println("Mouse clicked on sequence Row");
            selectionModel.toggleSelect(atomView.proteinNode);
        });

    }



    public ObjectBinding<Rectangle> createBoundingBoxBinding(Pane pane, Node node, Property[] properties) {


        final ObjectBinding<Rectangle> binding = new  ObjectBinding<Rectangle>(){

            {
                for (int i = 0; i < properties.length; i++) {
                    super.bind(properties[i]);
                }
            }
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

}
