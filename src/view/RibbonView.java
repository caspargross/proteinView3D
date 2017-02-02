package view;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;
import model.ProteinNode;

/**
 * Created by Caspar on 31.01.2017.
 * */

public class RibbonView extends Group {

    ProteinView proteinView;
    TriangleMesh mesh;


    RibbonView(ProteinView pV) {
        this.proteinView = pV;
        mesh = new TriangleMesh(VertexFormat.POINT_TEXCOORD);

        Point3D sourceCA;
        Point3D sourceCACB;

        Point3D targetCA;
        Point3D targetCACB;

        int i = 0;
        while (true) {

            ProteinNode pN = getNode(i);
            if (pN.getName().equals("CA")) {
                System.out.println(pN.getName());
                // Check for Glycine (Ignore if no CB available)
                if (!pN.getResName().equals("GLY")) {
                    sourceCA = new Point3D(pN.getX(), pN.getY(), pN.getZ());
                    break;
                }

            }
            i++;
        }
        ProteinNode cB =getNode(i+3);
        sourceCACB = new Point3D(cB.getX(), cB.getY(), cB.getZ()).subtract(sourceCA);


        while (i < proteinView.atomViewGroup.getChildren().size()-3) {
            System.err.println("I: " + i);
            targetCA = null;

            while (i < proteinView.atomViewGroup.getChildren().size()-3) {
                ProteinNode pN = getNode(i);
                if (pN.getName().equals("CA")) {
                    // Check for Glycine (Ignore if no CB available)
                    if (!pN.getResName().equals("GLY")) {
                        targetCA = new Point3D(pN.getX(), pN.getY(), pN.getZ());
                        break;
                    }

                }
                i++;
            }

            cB = getNode(i + 3);
            targetCACB = new Point3D(cB.getX(), cB.getY(), cB.getZ()).subtract(targetCA);


            mesh = new TriangleMesh(VertexFormat.POINT_TEXCOORD);
            float[] points = {
                    (float) sourceCA.add(sourceCACB).getX(), (float) sourceCA.add(sourceCACB).getY(), (float) sourceCA.add(sourceCACB).getZ(),
                    (float) sourceCA.subtract(sourceCACB).getX(), (float) sourceCA.subtract(sourceCACB).getY(), (float) sourceCA.subtract(sourceCACB).getZ(),
                    (float) targetCA.add(targetCACB).getX(), (float) targetCA.add(targetCACB).getY(), (float) targetCA.add(targetCACB).getZ(),
                    (float) targetCA.subtract(targetCACB).getX(), (float) targetCA.subtract(targetCACB).getY(), (float) targetCA.subtract(targetCACB).getZ(),
            };

            float[] texArray = {0, 0};
            int[] faces;

            faces = new int[]{
                    0, 0, 1, 0, 2, 0, // First triangle from source to upper side of target
                    2, 0, 1, 0, 0, 0, // Backside of first triangle

                    2, 0, 3, 0, 0, 0, // Second face, connects sCB, tCB and tMCB
                    0, 0, 3, 0, 2, 0  // Second face's back (same as above), connects sCB, tMCB and tCB
            };

            int[] smoothing = {
                    1, 2, 1, 2
            };
            mesh.getPoints().addAll(points);
            mesh.getFaces().addAll(faces);
            mesh.getTexCoords().addAll(texArray);
            //mesh.getFaceSmoothingGroups().addAll(smoothing);

            MeshView meshView = new MeshView(mesh);
            meshView.setDrawMode(DrawMode.FILL);

            PhongMaterial pM = new PhongMaterial(Color.TURQUOISE);
            pM.setSpecularColor(Color.TURQUOISE.brighter());
            meshView.setMaterial(pM);
            this.getChildren().add(meshView);

            sourceCA = targetCA;
            sourceCACB = targetCACB;
            i++;

        }

        System.err.println("Ribbon view finished");

    }


    private ProteinNode getNode(int i) {
        return ((AtomView) proteinView.atomViewGroup.getChildren().get(i)).proteinNode;
    }


}





