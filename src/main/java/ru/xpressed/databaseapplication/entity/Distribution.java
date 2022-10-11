package ru.xpressed.databaseapplication.entity;

import lombok.Data;
import ru.xpressed.databaseapplication.idclass.DistributionID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(DistributionID.class)
public class Distribution {
    @Id
    private int ID_Order;
    @Id
    private int ID_Waiter;
}
