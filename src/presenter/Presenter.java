package presenter;

import javafx.geometry.Point3D;
import javafx.scene.input.MouseButton;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import model.PdbParser;
import model.ProteinGraph;
import view.MainView;

import java.io.File;

/**
 * Created by Caspar on 23.12.2016.
 * Classic Presenter for non FXML stuff
 * This Presenter is used for actions in the GraphView and the SequenceView
*/

public class Presenter{

    final double SCALE_DELTA = 1.1;
    private double downX;
    private double downY;
    private double pivotX;
    private double pivotY;
    private double pivotZ;


    MainView view;
    ProteinGraph model;

    public Presenter (MainView mainView, ProteinGraph proteinGraph) {

        this.view = mainView;
        this.model = proteinGraph;

        setupRotateAndMove();
        setupZoom();
        setupMouseHover();
        updatePivotPoint();
    }

    public void setupRotateAndMove() {
        view.setOnMousePressed((me) -> {
            downX = me.getSceneX();
            downY = me.getSceneY();
            updatePivotPoint();
        });

        // Setup Rotate on left mouse button
        view.setOnMouseDragged((me) ->{
            if (me.getButton() == MouseButton.PRIMARY) {
                System.out.println("Mouse dragged left");
                System.out.println(pivotX +" "+ pivotY +" "+ pivotZ);
                double deltaX = downX-me.getSceneX();
                double deltaY = downY-me.getSceneY();

                Point3D directionVector = new Point3D(-deltaY, deltaX, 0);
                double angle = 0.5 * (Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));
                Rotate rotate = new Rotate(angle, pivotX, pivotY, pivotZ, directionVector);

                view.woldTransformProperty.setValue(rotate.createConcatenation(view.woldTransformProperty.getValue()));

                downX=me.getSceneX();
                downY=me.getSceneY();
            } else

            // Setup Drag Move on right mouse button
            if (me.getButton() == MouseButton.SECONDARY) {
                System.out.println("Mouse dragged right");
                double deltaX = downX-me.getSceneX();
                double deltaY = downY-me.getSceneY();

                Translate translate = new Translate(-deltaX, -deltaY, 0);

                view.woldTransformProperty.setValue(translate.createConcatenation(view.woldTransformProperty.getValue()));

                downX=me.getSceneX();
                downY=me.getSceneY();
            }

        });
    }

    // Zoom in Protein Scene
    public void setupZoom() {
        view.setOnScroll((se) -> {
            updatePivotPoint();

            System.out.println("Mouse scrolled");
            System.out.println(view.proteinView.getBoundsInParent().toString());
            if (se.getDeltaY() == 0) {
                return;
            }

            double scaleFactor = (se.getDeltaY() > 0) ? SCALE_DELTA : 1/ SCALE_DELTA;
            Scale scale = new Scale(scaleFactor, scaleFactor, scaleFactor, pivotX, pivotY, pivotZ);
            view.woldTransformProperty.setValue(scale.createConcatenation(view.woldTransformProperty.getValue()));
        });
    }

    // Hovering Mouse over Atoms displays additional information
    public void setupMouseHover(){
        view.bottomPane.setOnMouseEntered(me -> {
            System.out.println("HOVERING OVER SOMETHING");
        });
    }

    public void updatePivotPoint(){
        pivotX = view.proteinView.getBoundsInParent().getMaxX()-view.proteinView.getBoundsInParent().getWidth()/2;
        pivotY = view.proteinView.getBoundsInParent().getMaxY()-view.proteinView.getBoundsInParent().getHeight()/2;
        pivotZ = view.proteinView.getBoundsInParent().getMaxZ()-view.proteinView.getBoundsInParent().getDepth()/2;
    }




    // Button events in Main View
    private void createButtonEvents(){
        // Open File
        /**
        view.openFile.setOnAction(e ->{
            readPDB();
        });

        // Increase and Decrease Bond and Atom radius
        view.increaseAtomSize.setOnAction(e -> view.getProteinView().increaseAtomRadius());
        view.decreaseAtomSize.setOnAction(e -> view.getProteinView().decreaseAtomRadius());
        view.increaseBondSize.setOnAction(e -> view.getProteinView().increaseBondRadius());
        view.decreaseBondSize.setOnAction(e -> view.getProteinView().decreaseBondRadius());

        // Setup Button bindings
        view.showAtoms.selectedProperty().addListener((observable, oldValue, newValue) -> {
            model.showAtoms(newValue);
        });

        view.showBonds.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            model.showBonds(newValue);
        }));


        // Set initial view selections
        view.showAtoms.selectedProperty().set(true);
        view.showBonds.selectedProperty().set(true);
        view.showBackbone.selectedProperty().setValue(true);
        view.showResidues.selectedProperty().setValue(true);
        *
        //view.showBonds.setOnAction(e -> view.getProteinView().showBonds(view.showBonds.selectedProperty().get()));

        **/
        //view.showAtoms.setOnAction(e -> view.getProteinView().showAtoms(view.showAtoms.selectedProperty().get()));

    }

    public void readPDB() {

        // Open File Picker
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open PDB file");
        File file = fileChooser.showOpenDialog(null);
        PdbParser pdbParser = new PdbParser(file, model);

        // Add ProteinView and SequenceView to mainView
        model = pdbParser.getProteinGraph();
        model.assignBonds();

        view.setInitialTransform();
        //view.headerLabel.setText(model.getHeader());
        System.out.println("Finished PDB Parser action");

    }

}
