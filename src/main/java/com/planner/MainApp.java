package main.java.com.planner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.com.planner.DataService.DBConnection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApp extends Application {

    private Stage window;

    public static void main(String[] args) {

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(DBConnection::makeConnection);
        launch(args);
        DBConnection.closeConnection();
        if(!service.isTerminated()) service.shutdown();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.window = primaryStage;
        this.window.setTitle("Planner");

        initLoginPageLayout();
    }

    private void initLoginPageLayout() throws Exception {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/LoginPage.fxml"));
            window.getIcons().add(new Image(getClass().getResourceAsStream("resources/favicon-1.jpg")));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("resources/css/style.css").toExternalForm());
            window.setScene(scene);
            //window.setResizable(false);
            window.show();
    }

}
