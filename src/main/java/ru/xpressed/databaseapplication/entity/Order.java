package ru.xpressed.databaseapplication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Order {
    @Id
    private int ID_Order;
    private int ID_Dish;
}
