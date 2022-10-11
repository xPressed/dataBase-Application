package ru.xpressed.databaseapplication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
public class Shift {
    @Id
    private int ID_Shift;

    @NotEmpty(message = "Shift Type can not be empty!")
    private String Type;

    @NotEmpty(message = "Timetable can not be empty!")
    private String Timetable;
}
