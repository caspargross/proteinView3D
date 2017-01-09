package startup;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presenter.Presenter;
import view.MainView;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Create View
        MainView root = new MainView();

        // Create Presenter
        Presenter presenter = new Presenter(root);

        primaryStage.setTitle("Protein3DViewer");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();



    }

    public static void main(String[] args) {
        launch(args);
    }
}
