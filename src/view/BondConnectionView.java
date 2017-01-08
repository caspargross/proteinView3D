package view;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.Group;
import model.ProteinNode;

/**
 * Created by Caspar on 08.01.2017.
 * This is a quasi duplicate of AtomView with different colour and radius
 * This class serves to smooth the Cylinder connections when no Atom View is selected
 */
public class BondConnectionView extends Group{

    final double RADIUS_FACTOR = 0.05;

    ProteinNode proteinNode;
    Sphere sphere;

    public BondConnectionView(ProteinNode proteinNode){
        this.proteinNode=proteinNode;
        sphere=new Sphere();
        setupSphereColour();
        setSphereRadius();
        moveGroup();
        getChildren().add(sphere);
    }

    private void setupSphereColour(){
        PhongMaterial pm=new PhongMaterial();
        // Set colours acccording to element:
        pm.setDiffuseColor(Color.LIGHTBLUE);
        pm.setSpecularColor(Color.WHITE);
        sphere.setMaterial(pm);
    }

    private void moveGroup(){
        setTranslateX(proteinNode.getX());
        setTranslateY(proteinNode.getY());
        setTranslateZ(proteinNode.getZ());
    }

    private  void setSphereRadius(){
        sphere.setRadius(0.2);
    }

}
