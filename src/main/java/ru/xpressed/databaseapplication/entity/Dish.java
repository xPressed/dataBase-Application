package ru.xpressed.databaseapplication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Dish {
    @Id
    private int ID_Dish;
    private String Type;
    private int Calories;
    private String Compound;
    private int Price;
}
