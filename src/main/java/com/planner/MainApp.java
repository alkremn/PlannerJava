package main.java.com.planner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Planner");

        initLoginPageLayout();
    }

    private void initLoginPageLayout() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/LoginPage.fxml"));

            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("resources/css/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        launch(args);
    }
}
