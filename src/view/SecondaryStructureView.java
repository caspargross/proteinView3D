package view;
import com.sun.javafx.geom.transform.Affine3D;
import com.sun.javafx.geom.transform.BaseTransform;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import model.SecondaryStructure;

import java.util.ArrayList;

/**
 * Created by Caspar on 31.01.2017.
 */
public class SecondaryStructureView extends Group {

    String type;
    AtomView startAtom;
    AtomView endAtom;
    ArrayList<AtomView> atomViewList = new ArrayList<>();
    int direction; // Strand direction: 1 forward, -1 backward, 0 sheet

    public SecondaryStructureView(String type, ArrayList<AtomView> atomViewList) {


        this.type = type;
        this.atomViewList = atomViewList;
        this.startAtom = atomViewList.get(0);
        this.endAtom = atomViewList.get(atomViewList.size()-1);

        if (type.equals("HELIX")){
            MyLine3D helixView = new MyLine3D(
                    startAtom.getTranslateX(), startAtom.getTranslateY(), startAtom.getTranslateZ(),
                    endAtom.getTranslateX(), endAtom.getTranslateY(), endAtom.getTranslateZ(),
                    Color.CHARTREUSE
            );
            System.out.println("Created Helix View");
            helixView.cylinder.setRadius(1.5);
            getChildren().add(helixView);
        }

        if (type.equals("SHEET")){
            MyBand3D sheetView = new MyBand3D(
                    startAtom.getTranslateX(), startAtom.getTranslateY(), startAtom.getTranslateZ(),
                    endAtom.getTranslateX(), endAtom.getTranslateY(), endAtom.getTranslateZ(),
                    Color.CORNSILK
            );
            System.out.println("Created Helix View");
            getChildren().add(sheetView);
        }
    }





}
