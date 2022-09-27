package ru.xpressed.databaseapplication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Order {
    @Id
    private int ID_Order;
    private int ID_Dish;

    @Override
    public String toString() {
        return "Order{" +
                "ID_Order=" + ID_Order +
                ", ID_Dish=" + ID_Dish +
                '}';
    }
}
