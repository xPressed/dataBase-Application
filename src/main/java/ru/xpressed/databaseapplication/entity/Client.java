package ru.xpressed.databaseapplication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Client {
    @Id
    private int ID_Client;

    @NotNull(message = "ID of Order can not be null!")
    private int ID_Order;

    @NotEmpty(message = "Surname can not be empty!")
    private String Surname;

    @NotEmpty(message = "Name can not be empty!")
    private String Name;

    private String Patronymic;
}
