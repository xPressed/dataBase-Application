package ru.xpressed.databaseapplication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
public class Menu {
    @Id
    private int ID_Menu;

    @NotEmpty(message = "Menu Type can not be empty!")
    private String Type;
}
