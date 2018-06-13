package view;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import model.ProteinNode;

/**
 * Created by Caspar on 23.12.2016.
 */
public class AtomView extends Group{

    final double RADIUS_O = 6;
    final double RADIUS_N = 6.5;
    final double RADIUS_C = 7;
    final double RADIUS_FACTOR = 0.18;


    ProteinNode proteinNode;
    Sphere sphere;

    public AtomView(ProteinNode proteinNode){
        this.proteinNode=proteinNode;
        sphere=new Sphere();
        setupSphereColour();
        moveGroup();
        setSphereRadius();
        getChildren().add(sphere);
        setupMouseHover();
    }

    private void setupSphereColour(){
        PhongMaterial pm=new PhongMaterial();
        // Set colours acccording to element:
        if (proteinNode.getElement().equals("C")) pm.setDiffuseColor(Color.ALICEBLUE);
        if (proteinNode.getElement().equals("O")) pm.setDiffuseColor(Color.RED);
        if (proteinNode.getElement().equals("N")) pm.setDiffuseColor(Color.DARKGREEN);
        pm.setSpecularColor(Color.WHITE);
        sphere.setMaterial(pm);
    }

    private void setColor(String type){

    }

    private void moveGroup(){
        setTranslateX(proteinNode.getX());
        setTranslateY(proteinNode.getY());
        setTranslateZ(proteinNode.getZ());
    }

    private  void setSphereRadius(){
        if (proteinNode.getElement().equals("C")) sphere.setRadius(RADIUS_FACTOR *RADIUS_C);
        if (proteinNode.getElement().equals("O")) sphere.setRadius(RADIUS_FACTOR *RADIUS_O);
        if (proteinNode.getElement().equals("N")) sphere.setRadius(RADIUS_FACTOR *RADIUS_N);
    }

    private void setupMouseHover(){
        setOnMouseEntered(me -> {

        });
    }



}
