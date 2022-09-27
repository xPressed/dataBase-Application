package ru.xpressed.databaseapplication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Dish {
    @Id
    private int ID_Dish;
    private String Type;
    private int Calories;
    private String Compound;
    private int Price;

    @Override
    public String toString() {
        return "Dish{" +
                "ID_Dish=" + ID_Dish +
                ", Type='" + Type + '\'' +
                ", Calories=" + Calories +
                ", Compound='" + Compound + '\'' +
                ", Price=" + Price +
                '}';
    }
}
