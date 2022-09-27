package ru.xpressed.databaseapplication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Menu {
    @Id
    private int ID_Menu;
    private String Type;

    @Override
    public String toString() {
        return "Menu{" +
                "ID_Menu=" + ID_Menu +
                ", Type='" + Type + '\'' +
                '}';
    }
}
