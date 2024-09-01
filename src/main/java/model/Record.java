package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.security.auth.callback.Callback;
import java.time.LocalDate;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@ToString
public class Record {
    private String task;
    private LocalDate assignedDate;
    private LocalTime startTime;


    public Record(String task, LocalDate assignedDate, LocalTime startTime){
        this.task=task;
        this.assignedDate=assignedDate;
        this.startTime=startTime;
    }

    public void setCellFactory(Callback callback) {
    }
}
