package ru.xpressed.databaseapplication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Client {
    @Id
    private int ID_Client;
    private int ID_Order;
    private String Surname;
    private String Name;
    private String Patronymic;

    @Override
    public String toString() {
        return "Client{" +
                "ID_Client=" + ID_Client +
                ", ID_Order=" + ID_Order +
                ", Surname='" + Surname + '\'' +
                ", Name='" + Name + '\'' +
                ", Patronymic='" + Patronymic + '\'' +
                '}';
    }
}
