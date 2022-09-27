package ru.xpressed.databaseapplication.idclass;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class DistributionID implements Serializable {
    private int ID_Order;
    private int ID_Waiter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistributionID that = (DistributionID) o;
        return ID_Order == that.ID_Order && ID_Waiter == that.ID_Waiter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID_Order, ID_Waiter);
    }
}
