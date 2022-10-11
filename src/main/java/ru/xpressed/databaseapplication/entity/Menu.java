package ru.xpressed.databaseapplication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Menu {
    @Id
    private int ID_Menu;
    private String Type;
}
