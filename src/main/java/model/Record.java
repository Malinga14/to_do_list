package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.security.auth.callback.Callback;
import java.time.LocalDate;
import java.time.LocalTime;


@Data
@NoArgsConstructor
public class Record {
    private String task;
    private LocalDate assignedDate;
    private LocalTime startTime;
    private LocalDate completeDate;
    private LocalTime completeTime;
    private String state;

    public Record(String task, LocalDate assignedDate, LocalTime startTime, LocalDate completeDate, LocalTime completeTime, String state) {
        this.task=task;
        this.assignedDate=assignedDate;
        this.startTime=startTime;
        this.completeDate=completeDate;
        this.completeTime=completeTime;
        this.state=state;
    }

    public void setCellFactory(Callback callback) {
    }

    public String taskdone(){
        return this.task;
    }
    public String toString(){
        return (this.getAssignedDate()+"\t\t"+this.getTask());
    }
}
