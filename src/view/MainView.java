package view;

import com.sun.javafx.geom.transform.Affine3D;
import com.sun.javafx.geom.transform.BaseTransform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import model.PdbParser;
import model.ProteinGraph;

import java.io.File;

/**
 * Created by Caspar on 23.12.2016.
 * This is the main View class. All View elements are defined here.
 */

public class MainView extends BorderPane{

    // UI Elements
    public Label leftLabel = new Label("Left Sidebar");
    public Label headerLabel = new Label("");
    public Button openFile = new Button("Open");
    public Button increaseAtomSize = new Button("Atom ++");
    public Button decreaseAtomSize = new Button("Atom --");
    public Button increaseBondSize = new Button("Bond ++");
    public Button decreaseBondSize = new Button("Bond --");
    public CheckBox showAtoms = new CheckBox("show Atoms");
    public CheckBox showBonds = new CheckBox("show Bonds");

    // Model
    private ProteinGraph proteinGraph = new ProteinGraph();

    // Content views and Scene
    public SubScene viewScene;
    public ProteinView proteinView;
    public SequenceView sequenceView;

    // Content panes
    public ScrollPane sequencePane;
    public StackPane viewPane;
    public Pane bottomPane;
    public Pane topPane;

    // Transformation Property
    public Property<Transform> woldTransformProperty;

    public MainView() {
        // Create viewPane with ProteinView
        bottomPane = new Pane();
        topPane = new Pane(headerLabel);
        topPane.setPickOnBounds(false);
        viewPane = new StackPane(bottomPane, topPane);
        viewPane.setStyle("-fx-background-color: rgba(0, 200, 200, 0); -fx-background-radius: 10;");
        viewScene  = new SubScene(viewPane, 800, 800, true, SceneAntialiasing.BALANCED);
        viewScene.minWidth(400);
        viewScene.minHeight(400);

        setCamera();

        // Create sequencePane with protein sequence
        sequencePane = new ScrollPane();
        sequencePane.setMinWidth(200);
        sequencePane.setMaxWidth(200);
        sequencePane.setPadding(new Insets(10));
        sequencePane.setStyle("-fx-background-color: grey; -fx-background-radius: 10;");

        // Assign Panes to the Layout
        setTop(new HBox(
                openFile,
                increaseAtomSize, decreaseAtomSize,
                increaseBondSize, decreaseBondSize,
                showAtoms, showBonds
        ));
        setRight(sequencePane);
        setLeft(leftLabel);
        setCenter(viewScene);

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
            bottomPane.getTransforms().setAll(newValue);
        });

    }

    public void createNewViewFromPDB() {
        // Create empty view
        proteinView = new ProteinView(proteinGraph);
        sequenceView = new SequenceView(proteinGraph);

        // Open File Picker
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open PDB file");
        File file = fileChooser.showOpenDialog(null);
        PdbParser pdbParser = new PdbParser(file, proteinGraph);

        // Add ProteinView and SequenceView to mainView
        proteinGraph = pdbParser.getProteinGraph();
        proteinGraph.assignBonds();
        setInitialTransform(proteinView);
        bottomPane.getChildren().add(proteinView);
        sequencePane.setContent(sequenceView);
        headerLabel.setText(proteinGraph.getHeader());
    }

    public void setInitialTransform(ProteinView proteinView){
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
