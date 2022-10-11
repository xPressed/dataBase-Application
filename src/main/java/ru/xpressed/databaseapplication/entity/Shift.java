package ru.xpressed.databaseapplication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Shift {
    @Id
    private int ID_Shift;
    private String Type;
    private String Timetable;
}
