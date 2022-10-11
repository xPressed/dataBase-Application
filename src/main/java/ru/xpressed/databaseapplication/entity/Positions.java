package ru.xpressed.databaseapplication.entity;

import lombok.Data;
import ru.xpressed.databaseapplication.idclass.PositionsID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(PositionsID.class)
public class Positions {
    @Id
    private int ID_Menu;
    @Id
    private int ID_Dish;
}
