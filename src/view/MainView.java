package view;

import com.sun.javafx.geom.transform.Affine3D;
import com.sun.javafx.geom.transform.BaseTransform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import model.PdbParser;
import model.ProteinGraph;

import java.io.File;

/**
 * Created by Caspar on 23.12.2016.
 * This is the main View class. All View elements are defined here.
 */

public class MainView extends StackPane{

    // Model (ProteinGraph)
    public ProteinGraph model;

    // UI Elements
    /**
     * public Label leftLabel = new Label("Left Sidebar");
    public Label headerLabel = new Label("");
    public Button openFile = new Button("Open");
    public Button increaseAtomSize = new Button("Atom ++");
    public Button decreaseAtomSize = new Button("Atom --");
    public Button increaseBondSize = new Button("Bond ++");
    public Button decreaseBondSize = new Button("Bond --");
    public CheckBox showAtoms = new CheckBox("show Atoms");
    public CheckBox showBonds = new CheckBox("show Bonds");
    public CheckBox showBackbone = new CheckBox("Backbone");
    public CheckBox showResidues = new CheckBox("CA and CB");
    public CheckBox showSidechains = new CheckBox("Complete Res.");
    public CheckBox showTriangles = new CheckBox("Triangles");
    public CheckBox showSecondaryStructure = new CheckBox("sec. Structure");

    public VBox viewElements = new VBox(leftLabel, showAtoms, showBonds, showBackbone, showResidues,
            showSidechains, showTriangles, showSecondaryStructure);
     **/

    // Content views and Scene
    public SubScene viewScene;
    public ProteinView proteinView;
    //public SequenceView sequenceView;

    // Content panes
    public ScrollPane sequencePane;
    public StackPane viewPane;
    public Pane bottomPane;
    public Pane topPane;

    // Transformation Property
    public Property<Transform> woldTransformProperty;

    public MainView(ProteinGraph proteinGraph) {
        this.model = proteinGraph;

        // Create new views
        proteinView = new ProteinView(model);
        //sequenceView = new SequenceView(proteinView);

        // Setup viewScenes with ProteinView
        viewScene  = new SubScene(proteinView, 800, 800, true, SceneAntialiasing.BALANCED);
        viewScene.minWidth(400);
        viewScene.minHeight(400);


        // Setup TopPane and BottomPane
        topPane = new Pane();
        topPane.getChildren().add(new Label("TOPPANE"));
        topPane.setPickOnBounds(false);
        bottomPane = new Pane();
        bottomPane.getChildren().add(viewScene);
        bottomPane.setPickOnBounds(false);
        viewPane = new StackPane(bottomPane, topPane);
        viewPane.setStyle("-fx-background-color: rgba(0, 200, 200, 0); -fx-background-radius: 10;");
        viewPane.setAlignment(bottomPane, Pos.TOP_LEFT);
        //bottomPane.setStyle("-fx-background-color: rgba(30, 70, 200, 1); -fx-background-radius: 10;");
        viewScene.setStyle("-fx-background-color: rgba(30, 70, 200, 1); -fx-background-radius: 10;");
        setCamera();

        // Create sequencePane with protein sequence
        /**sequencePane = new ScrollPane();
        sequencePane.setMinWidth(200);
        sequencePane.setMaxWidth(200);
        sequencePane.setPadding(new Insets(10));
        sequencePane.setStyle("-fx-background-color: grey; -fx-background-radius: 10;");
        sequencePane.setContent(sequenceView);
         **/

        // Assign Panes to the Layout
        /** setTop(new HBox(
                openFile,
                increaseAtomSize, decreaseAtomSize,
                increaseBondSize, decreaseBondSize
        ));
        setRight(sequencePane);
        setLeft(viewElements);
        setCenter(viewPane);
        **/
        getChildren().add(viewPane);

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


    public void setInitialTransform(){
        viewPane.setTranslateX(-viewScene.getWidth()/2);
        viewPane.setTranslateY(-viewScene.getHeight()/2);
        bottomPane.setTranslateX(-proteinView.getMidX()+viewScene.getWidth()/2);
        bottomPane.setTranslateY(-proteinView.getMidY()+viewScene.getHeight()/2);

    }

    private void setCamera(){
        // Set Camera settings for SubScene
        Camera camera = new PerspectiveCamera(true);
        camera.setFarClip(50000);
        camera.setTranslateZ(-1000);
        viewScene.setCamera(camera);
    }

    public ProteinView getProteinView() {
        return proteinView;
    }

}
