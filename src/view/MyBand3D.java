package view;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Created by Caspar on 11.12.2016.
 */
public class MyBand3D extends Group {


    double startX;
    double startY;
    double startZ;
    double endX;
    double endY;
    double endZ;
    Color color;
    Box box;
    Point3D startpoint;
    Point3D endpoint;

    // Constructor
    public MyBand3D(double startX, double startY, double startZ,
                    double endX, double endY, double endZ, Color color) {

        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
        this.color = color;
        this.box = new Box();
        boxRoutine();

    }



    // Collection of all methods to move, scale, rotate and setup the box
    private void boxRoutine() {
        startpoint = new Point3D(startX, startY, startZ);
        endpoint = new Point3D(endX, endY, endZ);

        moveBox(box);
        rotateBox(box);
        scaleBox(box);
        setupBox(box);
        getChildren().add(box);
    }

    private void rotateBox(Box box){
        Point3D directionVector = endpoint.subtract(startpoint);
        Point3D rotationAxis = directionVector.crossProduct(0, -1, 0);
        System.out.println("Direction Vector: " + directionVector.toString());
        System.out.println("Rotation Vector: " + rotationAxis.toString());

        double angle = directionVector.angle(0, 1, 0);
        System.out.println("Angle: "+ angle);
        Rotate rotate = new Rotate(angle, rotationAxis);
        box.getTransforms().add(rotate);
    }

    private void moveBox(Box box){
        Point3D midpoint = startpoint.midpoint(endpoint);
        Translate translate = new Translate(midpoint.getX(), midpoint.getY(), midpoint.getZ());
        box.getTransforms().add(translate);
    }

    private void scaleBox(Box box){
        box.setHeight(startpoint.distance(endpoint));
        box.setWidth(0.2);
        box.setDepth(1);
    }

    private void setupBox(Box box){
        PhongMaterial pm = new PhongMaterial();
        pm.setDiffuseColor(color);
        pm.setSpecularColor(Color.WHITE);
        box.setMaterial(pm);
    }

}
