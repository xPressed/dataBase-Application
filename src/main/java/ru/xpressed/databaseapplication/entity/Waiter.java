package ru.xpressed.databaseapplication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Data
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
}
