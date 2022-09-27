package ru.xpressed.databaseapplication.entity;

import lombok.Getter;
import lombok.Setter;
import ru.xpressed.databaseapplication.idclass.DistributionID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@Entity
@IdClass(DistributionID.class)
public class Distribution {
    @Id
    private int ID_Order;
    @Id
    private int ID_Waiter;

    @Override
    public String toString() {
        return "Distribution{" +
                "ID_Order=" + ID_Order +
                ", ID_Waiter=" + ID_Waiter +
                '}';
    }
}
