package main.java.com.planner.controller.calendar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import main.java.com.planner.model.Day;

import java.io.IOException;

public class DayListViewCell extends ListCell<Day> {

    @FXML
    private Label dayLabel;

    @FXML
    private GridPane gridPane;

    FXMLLoader mLLoader;

    @Override
    protected void updateItem(Day day, boolean empty) {
        super.updateItem(day, empty);

        if(empty || day == null || day.getDate() == null){
            setText(null);
        } else{
            if(mLLoader == null){
                mLLoader = new FXMLLoader(getClass().getResource("../../fxml/ListCell.fxml"));
                mLLoader.setController(this);
            }

            try{
                mLLoader.load();
            } catch (IOException e){
                e.printStackTrace();
            }
            dayLabel.setText(String.valueOf(day.getDate().getDayOfMonth()));
            setGraphic(gridPane);
        }
    }
}
