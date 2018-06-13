package view;

import com.sun.javafx.geom.transform.Affine3D;
import com.sun.javafx.geom.transform.BaseTransform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.scene.transform.Transform;
import model.MyBarChart;
import model.MySelectionModel;
import model.ProteinGraph;
import model.ProteinNode;


/**
 * Created by Caspar on 23.12.2016.
 * This is the main View class. All View elements are defined here.
 */

public class MainView extends StackPane {

    // Model (ProteinGraph)
    public ProteinGraph model;
    public MySelectionModel<ProteinNode> selectionModel;
    public MyBarChart barChart = new MyBarChart();


    // Content views and Scene
    public SubScene viewScene;
    public ProteinView proteinView;
    public SequenceView sequenceView;

    // Content panes
    //public StackPane viewPane;
    public Pane bottomPane;
    public Pane topPane;

    // Transformation Property
    public Property<Transform> woldTransformProperty;

    public MainView(ProteinGraph proteinGraph) {

        this.model = proteinGraph;
        this.selectionModel = new MySelectionModel<>();

        // Setup Main Pane
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
        this.setStyle("-fx-background-color : White");

        // Create new views
        this.proteinView = new ProteinView(model, selectionModel);
        this.sequenceView = new SequenceView(proteinView, selectionModel);

        // Setup viewScenes with ProteinView
        viewScene = new SubScene(proteinView, this.getWidth(), this.getHeight(), true, SceneAntialiasing.BALANCED);
        viewScene.widthProperty().bind(this.widthProperty());
        viewScene.heightProperty().bind(this.heightProperty());
        viewScene.setPickOnBounds(false);


        // Setup TopPane and BottomPane
        topPane = new Pane();
        topPane.setPickOnBounds(false);
        this.setPickOnBounds(false);

        bottomPane = new Pane();
        bottomPane.setPickOnBounds(true);
        bottomPane.getChildren().add(viewScene);


        //viewPane = new StackPane(bottomPane, topPane);
        //viewPane.setStyle("-fx-background-color: rgba(0, 200, 200, 0); -fx-background-radius: 10;");
        //viewPane.setAlignment(bottomPane, Pos.TOP_LEFT);

        getChildren().addAll(bottomPane, topPane);


        setCamera();
        //getChildren().add(viewPane);

        // Add BarChart


        // Add WorldTransform
        woldTransformProperty = new SimpleObjectProperty<>(new Transform() {
            @Override
            public void impl_apply(Affine3D t) {

            }

            @Override
            public BaseTransform impl_derive(BaseTransform t) {
                return null;
            }
        });

        woldTransformProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println("woldTransform Rotated");
            proteinView.getTransforms().setAll(newValue);
        });

        setupBoundingBoxes();


    }


    private void setCamera() {

        // Set Camera settings for SubScene
        Camera camera = new PerspectiveCamera(true);
        camera.setFarClip(50000);
        camera.setTranslateZ(-1000);
        viewScene.setCamera(camera);
    }



    private void setupBoundingBoxes(){

       selectionModel.getSelectedItems().addListener((ListChangeListener<ProteinNode>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(node -> {
                        BoundingBoxes2D bB2D = new BoundingBoxes2D(bottomPane, proteinView.findAtomViewFor(node), woldTransformProperty, viewScene, selectionModel);
                        topPane.getChildren().add(bB2D);
                    });
                }

                if (c.wasRemoved()) {
                    c.getRemoved().forEach(node -> {
                       topPane.getChildren().remove(c.getFrom(), c.getTo() + c.getRemovedSize());
                    });
                }
            }
        });
}   }