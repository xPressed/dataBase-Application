package ru.xpressed.databaseapplication.entity;

import lombok.Getter;
import lombok.Setter;
import ru.xpressed.databaseapplication.idclass.PositionsID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@Entity
@IdClass(PositionsID.class)
public class Positions {
    @Id
    private int ID_Menu;
    @Id
    private int ID_Dish;

    @Override
    public String toString() {
        return "Positions{" +
                "ID_Menu=" + ID_Menu +
                ", ID_Dish=" + ID_Dish +
                '}';
    }
}
