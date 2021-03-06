package startup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Layout: Classic MVP
public class Main extends Application {

    public static void main(String[] args){
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Layout.fxml"));
        Parent root = loader.load();
        loader.setRoot(root);
        primaryStage.setTitle("Protein3DViewer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
