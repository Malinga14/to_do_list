package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Record;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class CompletedTaskView implements Initializable {

    @FXML
    private TableView<Record> tblCompletedTasks;

    @FXML
    private TableColumn<?, ?> colTasks;

    @FXML
    private TableColumn<?, ?> colAssignedDate;

    @FXML
    private TableColumn<?, ?> colCompletedDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
