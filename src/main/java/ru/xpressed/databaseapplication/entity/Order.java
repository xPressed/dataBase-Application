package ru.xpressed.databaseapplication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Order {
    @Id
    private int ID_Order;

    @NotNull(message = "ID of Dish can not be null!")
    private int ID_Dish;
}
