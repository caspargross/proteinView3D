package presenter;

/**
 * Created by Caspar on 26.01.2017.
 * This is the controller class to hande stuff in the FXML file
 *
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import model.ProteinGraph;
import view.MainView;


public class Controller {


        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private AnchorPane proteinView;

        @FXML
        private MenuItem menuOpen;

        @FXML
        private TitledPane x5;



        @FXML
        void initialize() {
            assert proteinView != null : "fx:id=\"proteinView\" was not injected: check your FXML file 'Layout.fxml'.";
            assert x5 != null : "fx:id=\"x5\" was not injected: check your FXML file 'Layout.fxml'.";



            // Create Model
            ProteinGraph model = new ProteinGraph();

            // Create View
            MainView root = new MainView(model);

            // Create Presenter
            Presenter presenter = new Presenter(root, model);

            proteinView.getChildren().add(root);

            menuOpen.setOnAction(event -> {
                presenter.readPDB();
                System.out.println("Open File Button pressed");
            });

        }

    }

