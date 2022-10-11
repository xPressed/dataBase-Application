package ru.xpressed.databaseapplication.idclass;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class PositionsID implements Serializable {
    private int ID_Menu;
    private int ID_Dish;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionsID that = (PositionsID) o;
        return ID_Menu == that.ID_Menu && ID_Dish == that.ID_Dish;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID_Menu, ID_Dish);
    }
}
