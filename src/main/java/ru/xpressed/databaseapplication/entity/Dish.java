package ru.xpressed.databaseapplication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Dish {
    @Id
    private int ID_Dish;

    @NotEmpty(message = "Type can not be empty!")
    private String Type;

    @NotNull(message = "Calories can not be empty!")
    private int Calories;

    @NotEmpty(message = "Compound can not be empty!")
    private String Compound;

    @NotNull(message = "Price can not be empty!")
    private int Price;
}
