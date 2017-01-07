package view;

import com.sun.javafx.geom.transform.Affine3D;
import com.sun.javafx.geom.transform.BaseTransform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import model.PdbParser;
import model.ProteinGraph;

import java.io.File;

/**
 * Created by Caspar on 23.12.2016.
 */
public class MainView extends BorderPane{

    public Label leftLabel = new Label("Left Sidebar");
    Button openFile = new Button("Open");
    public Button increaseAtomSize = new Button("Atom ++");
    public Button decreaseAtomSize = new Button("Atom --");
    Label rightLabel = new Label ("Righ Sidear");
    private ProteinGraph proteinGraph = new ProteinGraph();
    private ProteinView proteinView;
    public Pane viewPane;
    public SubScene viewScene;
    public Property<Transform> woldTransformProperty;

    public MainView() {

        viewPane = new Pane();
        viewPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-background-radius: 10;");
        viewScene  = new SubScene(viewPane, 800, 800, true, SceneAntialiasing.BALANCED);
        setCamera();

        openFile.setOnAction(e ->{
            proteinView = new ProteinView(proteinGraph);
            pickPDBFile(proteinGraph);
            proteinGraph.assignBonds();
            viewPane.getChildren().add(proteinView);
        });


        setCenter(viewScene);
        setTop(new HBox(openFile, increaseAtomSize, decreaseAtomSize));
        setRight(rightLabel);
        setLeft(leftLabel);

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
            viewPane.getTransforms().setAll(newValue);
        });

    }

    private void pickPDBFile(ProteinGraph proteinGraph){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open PDB file");
        File file = fileChooser.showOpenDialog(null);
        PdbParser pdbParser = new PdbParser(file, proteinGraph);
        proteinGraph = pdbParser.getProteinGraph();
    }

    private void setCamera(){
        // Set Camera and Scene Settings
        Camera camera = new PerspectiveCamera(true);
        camera.setFarClip(50000);
        camera.setTranslateZ(-2000);
        viewScene.setCamera(camera);
    }

    public ProteinView getProteinView() {
        return proteinView;
    }
}
