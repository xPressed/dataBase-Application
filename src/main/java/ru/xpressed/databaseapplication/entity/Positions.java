package ru.xpressed.databaseapplication.entity;

import lombok.Data;
import ru.xpressed.databaseapplication.idclass.PositionsID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;

@Data
@Entity
@IdClass(PositionsID.class)
public class Positions {
    @Id
    @NotNull(message = "ID of Menu can not be null!")
    private int ID_Menu;
    @Id
    @NotNull(message = "ID of Dish can not be null!")
    private int ID_Dish;
}
