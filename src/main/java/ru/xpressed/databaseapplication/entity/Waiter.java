package ru.xpressed.databaseapplication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Entity
public class Waiter {
    @Id
    private int ID_Waiter;

    @NotEmpty(message = "Surname can not be empty!")
    private String Surname;

    @NotEmpty(message = "Name can not be empty!")
    private String Name;

    private String Patronymic;

    @NotEmpty(message = "Gender can not be empty!")
    private String Gender;

    @NotNull(message = "Date Of Birth can not be null!")
    private Date DateOfBirth;

    @NotNull(message = "ID of Shift can not be null")
    private int ID_Shift;
}
