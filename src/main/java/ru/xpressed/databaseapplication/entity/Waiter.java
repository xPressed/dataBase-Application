package ru.xpressed.databaseapplication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Getter
@Setter
@Entity
public class Waiter {
    @Id
    private int ID_Waiter;
    private String Surname;
    private String Name;
    private String Patronymic;
    private String Gender;
    private Date DateOfBirth;
    private int ID_Shift;

    @Override
    public String toString() {
        return "Waiter{" +
                "ID_Waiter=" + ID_Waiter +
                ", Surname='" + Surname + '\'' +
                ", Name='" + Name + '\'' +
                ", Patronymic='" + Patronymic + '\'' +
                ", Gender='" + Gender + '\'' +
                ", DateOfBirth=" + DateOfBirth +
                ", ID_Shift=" + ID_Shift +
                '}';
    }
}
