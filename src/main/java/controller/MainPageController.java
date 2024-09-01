package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Record;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class MainPageController {

    public JFXTextField txtTask;
    public JFXListView jFXListView;

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
        addTask();
    }

    public void btnAddTask2(ActionEvent actionEvent) throws SQLException {
        addTask();
    }

    public void txtTask(ActionEvent actionEvent) {
        addTask();
    }

    public void btnFuturePlans(ActionEvent actionEvent) {
    }

    private void addTask(){
        if(txtTask.getText().isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Invalid Input!\n    Try Again....").show();
        }else {
            Record record = new Record(
                    txtTask.getText(),
                    LocalDate.now(),
                    LocalTime.now()
            );

            record.setCellFactory(new Callback() {


            });

            try {
                String SQL = "INSERT INTO task_list VALUES(?,?,?)";
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement psTm = connection.prepareStatement(SQL);
                psTm.setObject(1, record.getTask());
                psTm.setObject(2, record.getAssignedDate());
                psTm.setObject(3, record.getStartTime());

                boolean b = psTm.executeUpdate() > 0;


                if (b) {
                    txtTask.setText(null);
                    new Alert(Alert.AlertType.INFORMATION, "Your Task Added!").show();
                    loadTable();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void loadTable(){
        ObservableList<String> stringObservableList = FXCollections.observableArrayList();


        try {
            String SQL = "SELECT * FROM task_list";
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()) {
                Record record = new Record(
                        resultSet.getString("task"),
                        resultSet.getDate("assigned_date").toLocalDate(),
                        resultSet.getTime("start_time").toLocalTime()
                );

                stringObservableList.add(record.toString());
                jFXListView.setItems(stringObservableList);
                System.out.println(stringObservableList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}
