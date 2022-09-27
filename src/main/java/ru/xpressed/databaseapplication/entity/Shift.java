package ru.xpressed.databaseapplication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Shift {
    @Id
    private int ID_Shift;
    private String Type;
    private String Timetable;

    @Override
    public String toString() {
        return "Shift{" +
                "ID_Shift=" + ID_Shift +
                ", Type='" + Type + '\'' +
                ", Timetable='" + Timetable + '\'' +
                '}';
    }
}
