package presenter;

import javafx.geometry.Point3D;
import javafx.scene.input.MouseButton;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import view.MainView;
import view.ProteinView;

/**
 * Created by Caspar on 23.12.2016.
 */
public class Presenter{

    final double SCALE_DELTA = 1.1;
    private double downX;
    private double downY;
    private double zPane = 0;


    MainView view;
    ProteinView proteinView;


    public Presenter (MainView mainView) {

        this.view = mainView;
        proteinView = view.getProteinView();
        setupRotateAndMove();
        setupZoom();
    }

    public void setupRotateAndMove() {
        view.viewScene.setOnMousePressed((me) -> {
            downX = me.getSceneX();
            downY = me.getSceneY();
        });

        // Setup Rotate on left mouse button
        view.setOnMouseDragged((me) ->{
            if (me.getButton() == MouseButton.PRIMARY) {
                System.out.println("Mouse dragged left");
                double deltaX = downX-me.getSceneX();
                double deltaY = downY-me.getSceneY();

                Point3D directionVector = new Point3D(deltaX, deltaY, zPane).crossProduct(0, 0, -1);
                Rotate rotate = new Rotate(1.5, directionVector);

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

        createAttachments();
    }

    // Zoom in Protein Scene
    public void setupZoom() {
        view.viewScene.setOnScroll((se) -> {
            System.out.println("Mouse scrolled");
            if (se.getDeltaY() == 0) {
                return;
            }

            double scaleFactor = (se.getDeltaY() > 0) ? SCALE_DELTA : 1/ SCALE_DELTA;
            Scale scale = new Scale(scaleFactor, scaleFactor, scaleFactor);
            view.woldTransformProperty.setValue(scale.createConcatenation(view.woldTransformProperty.getValue()));
        });
    }

    // Button events in Main View
    public void createAttachments(){
        view.increaseAtomSize.setOnAction(e ->{
            System.out.println("Size increase button pressed");
            view.getProteinView().increaseAtomRadius();
        });

        view.decreaseAtomSize.setOnAction(e ->{
            System.out.println("Size decrease button pressed");
            view.getProteinView().decreaseAtomRadius();
        });

    }
}
