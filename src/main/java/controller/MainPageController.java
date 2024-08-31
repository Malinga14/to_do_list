package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class MainPageController {

    public void loadTable(){
        //.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
    
    public void btnCompletedTask(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/completed_task_view.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnImportant(ActionEvent actionEvent) {

    }

    public void btnAddTask1(ActionEvent actionEvent) {

    }

    public void txtTask(ActionEvent actionEvent) {
    }

    public void btnAddTask2(ActionEvent actionEvent) {
    }

    public void btnFuturePlans(ActionEvent actionEvent) {
    }
}
