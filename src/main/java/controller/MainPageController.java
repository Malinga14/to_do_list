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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Record;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class MainPageController {

    public JFXTextField txtTask;
    public JFXListView<String> jFXListView;

    public void btnCompletedTask(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/completed_task_view.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                    LocalTime.now(),
                    LocalDate.now(),
                    LocalTime.now(),
                    "Not Completed"
            );

            liveCheckBox();

            try {
                String SQL = "INSERT INTO task_list VALUES(?,?,?,?,?,?)";
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement psTm = connection.prepareStatement(SQL);
                psTm.setObject(1, record.getTask());
                psTm.setObject(2, record.getAssignedDate());
                psTm.setObject(3, record.getStartTime());
                psTm.setObject(4, record.getCompleteDate());
                psTm.setObject(5, record.getCompleteTime());
                psTm.setObject(6,record.getState());

                boolean b = psTm.executeUpdate() > 0;


                if (b) {
                    txtTask.setText(null);
                    new Alert(Alert.AlertType.INFORMATION, "Your Task Added!").show();
                    loadTable();
                }else{
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
            String SQL = "SELECT * FROM task_list WHERE state = 'Not Completed'";
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
                stringObservableList.add(String.valueOf(record));
                jFXListView.setItems(stringObservableList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void liveCheckBox(){
        jFXListView.setCellFactory(new javafx.util.Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new ListCell<>() {
                    private final HBox hbox = new HBox();
                    private final CheckBox checkBox = new CheckBox();
                    private final Text text = new Text();


                    {
                        hbox.getChildren().addAll(checkBox,text);
                        hbox.setSpacing(10);

                        checkBox.setOnAction(event -> {
                            if (checkBox.isSelected()) {
                                try {
                                    String[] task=text.getText().split("\t\t");
                                    String sql ="UPDATE task_list SET state='Completed', complete_date=?, complete_time=? WHERE task=?";
                                    Connection connection = db.DBConnection.getInstance().getConnection();
                                    PreparedStatement psTm = connection.prepareStatement(sql);
                                    psTm.setObject(1,LocalDate.now());
                                    psTm.setObject(2,LocalTime.now());
                                    psTm.setObject(3,task[1]);
                                    if(psTm.executeUpdate()>0){
                                        checkBox.setSelected(false);
                                        new Alert(Alert.AlertType.INFORMATION,"Task Done!").show();
                                        loadTable();
                                    }else {
                                        new Alert(Alert.AlertType.INFORMATION, "Task Not Done!").show();
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            text.setText(item);
                            setGraphic(hbox);
                        }
                    }
                };

            }
        });
    }


}
