package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@ToString
public class CompletedRecords {
    private String task;
    private LocalDate assignedDate;
    private LocalTime startTime;

}
