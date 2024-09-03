package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import model.Record;

import java.net.URL;
import java.sql.*;

import java.util.ResourceBundle;

public class CompletedTaskView implements Initializable {

    private final ObservableList<Record> recordObservableList=FXCollections.observableArrayList();
    @FXML
    private TableView<Record> tblCompletedTasks;

    @FXML
    private TableColumn<?, ?> colTasks;

    @FXML
    private TableColumn<?, ?> colAssignedDate;

    @FXML
    private TableColumn<?,?> colAssignedTime;

    @FXML
    private TableColumn<?, ?> colCompletedDate;

    @FXML
    private TableColumn<?,?> colCompletedTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public CompletedTaskView(){
        loadTable();
    }
    public void loadTable(){
        try {
            String SQL = "SELECT * FROM task_list WHERE state = 'Completed'";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()) {
                Record record = new Record(
                        resultSet.getString("task"),
                        resultSet.getDate("assigned_date").toLocalDate(),
                        resultSet.getTime("start_time").toLocalTime(),
                        resultSet.getDate("complete_date").toLocalDate(),
                        resultSet.getTime("complete_time").toLocalTime(),
                        resultSet.getString("state")
                );
                System.out.println(record.taskdone());
                recordObservableList.add(record);



            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setResult(){
        colTasks.setCellValueFactory(new PropertyValueFactory<>("task"));
        colAssignedDate.setCellValueFactory(new PropertyValueFactory<>("assigned_date"));
        colAssignedTime.setCellValueFactory(new PropertyValueFactory<>("start_time"));
        colCompletedDate.setCellValueFactory(new PropertyValueFactory<>("complete_date"));
        colCompletedTime.setCellValueFactory(new PropertyValueFactory<>("complete_time"));
        tblCompletedTasks.setItems(recordObservableList);

    }



    public void Reaload(ActionEvent actionEvent) {
    }
}
