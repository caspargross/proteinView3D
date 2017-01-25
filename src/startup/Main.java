package startup;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ProteinGraph;
import presenter.Presenter;
import view.MainView;

// Layout: Classic MVP
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Create Model
        ProteinGraph model = new ProteinGraph();

        // Create View
        MainView root = new MainView(model);

        // Create Presenter
        new Presenter(root, model);

        primaryStage.setTitle("Protein3DViewer");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }
}
