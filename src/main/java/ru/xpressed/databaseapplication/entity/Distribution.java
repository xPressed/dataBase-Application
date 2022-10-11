package ru.xpressed.databaseapplication.entity;

import lombok.Data;
import ru.xpressed.databaseapplication.idclass.DistributionID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;

@Data
@Entity
@IdClass(DistributionID.class)
public class Distribution {
    @Id
    @NotNull(message = "ID of Order can not be null!")
    private int ID_Order;
    @Id
    @NotNull(message = "ID of Waiter can not be null!")
    private int ID_Waiter;
}
