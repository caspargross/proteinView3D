package view;

import com.sun.javafx.geom.transform.Affine3D;
import com.sun.javafx.geom.transform.BaseTransform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.transform.Transform;
import model.ProteinGraph;


/**
 * Created by Caspar on 23.12.2016.
 * This is the main View class. All View elements are defined here.
 */

public class MainView extends StackPane {

    // Model (ProteinGraph)
    public ProteinGraph model;

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

        // Setup Main Pane
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
        this.setStyle("-fx-background-color : White");

        // Create new views
        proteinView = new ProteinView(model);
        sequenceView = new SequenceView(proteinView);

        // Setup viewScenes with ProteinView
        viewScene = new SubScene(proteinView, this.getWidth(), this.getHeight(), true, SceneAntialiasing.BALANCED);
        viewScene.widthProperty().bind(this.widthProperty());
        viewScene.heightProperty().bind(this.heightProperty());


        // Setup TopPane and BottomPane
        topPane = new Pane();
        topPane.setPickOnBounds(false);

        bottomPane = new Pane();
        bottomPane.getChildren().add(viewScene);
        bottomPane.setPickOnBounds(false);

        //viewPane = new StackPane(bottomPane, topPane);
        //viewPane.setStyle("-fx-background-color: rgba(0, 200, 200, 0); -fx-background-radius: 10;");
        //viewPane.setAlignment(bottomPane, Pos.TOP_LEFT);

        getChildren().addAll(bottomPane, topPane);


        setCamera();
        //getChildren().add(viewPane);

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


    }

    public void setInitialTransform() {


    }

    private void setCamera() {
        // Set Camera settings for SubScene
        Camera camera = new PerspectiveCamera(true);
        camera.setFarClip(50000);
        camera.setTranslateZ(-1000);
        viewScene.setCamera(camera);
    }
}
