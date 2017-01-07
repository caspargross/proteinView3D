package view;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Created by Caspar on 11.12.2016.
 */
public class MyLine3D extends Group {

    double startX;
    double startY;
    double startZ;
    double endX;
    double endY;
    double endZ;
    Color color;
    Cylinder cylinder;
    Point3D startpoint;
    Point3D endpoint;

    // Constructor
    public MyLine3D(double startX, double startY, double startZ,
                    double endX, double endY, double endZ, Color color) {

        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
        this.color = color;
        this.cylinder = new Cylinder();
        cylinderRoutine();

    }



    // Collection of all methods to move, scale, rotate and setup the cylinder
    private void cylinderRoutine() {
        startpoint = new Point3D(startX, startY, startZ);
        endpoint = new Point3D(endX, endY, endZ);

        moveCylinder(cylinder);
        rotateCylinder(cylinder);
        scaleCylinder(cylinder);
        setupCylinder(cylinder);
        getChildren().add(cylinder);

    }

    private void rotateCylinder(Cylinder cylinder){
        Point3D directionVector = endpoint.subtract(startpoint);
        Point3D rotationAxis = directionVector.crossProduct(0, -1, 0);
        System.out.println("Direction Vector: " + directionVector.toString());
        System.out.println("Rotation Vector: " + rotationAxis.toString());

        double angle = directionVector.angle(0, 1, 0);
        System.out.println("Angle: "+ angle);
        Rotate rotate = new Rotate(angle, rotationAxis);
        cylinder.getTransforms().add(rotate);

    }

    private void moveCylinder(Cylinder cylinder){
        Point3D midpoint = startpoint.midpoint(endpoint);
        Translate translate = new Translate(midpoint.getX(), midpoint.getY(), midpoint.getZ());
        cylinder.getTransforms().add(translate);

    }

    private void scaleCylinder(Cylinder cylinder){
        cylinder.setHeight(startpoint.distance(endpoint));
        cylinder.setRadius(0.2);
    }

    private void setupCylinder(Cylinder cylinder){
        PhongMaterial pm = new PhongMaterial();
        pm.setDiffuseColor(color);
        pm.setSpecularColor(Color.WHITE);
        cylinder.setMaterial(pm);

    }

}
