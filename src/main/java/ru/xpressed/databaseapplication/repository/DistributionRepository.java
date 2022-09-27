package ru.xpressed.databaseapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.xpressed.databaseapplication.entity.Distribution;
import ru.xpressed.databaseapplication.idclass.DistributionID;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DistributionRepository extends JpaRepository<Distribution, DistributionID> {
    @Query(value = "SELECT * FROM Distribution", nativeQuery = true)
    List<Distribution> findAll();

    @Query(value = "SELECT * FROM Distribution WHERE (:idOrder is null or ID_Order = :idOrder) AND" +
            "(:idWaiter is null or ID_Waiter = :idWaiter)", nativeQuery = true)
    List<Distribution> find(Integer idOrder, Integer idWaiter);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Distribution (ID_Order, ID_Waiter) VALUES (:idOrder, :idWaiter)", nativeQuery = true)
    void insert(int idOrder, int idWaiter);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Distribution WHERE (:idOrder is null or ID_Order = :idOrder) AND" +
            "(:idWaiter is null or ID_Waiter = :idWaiter)", nativeQuery = true)
    void delete(Integer idOrder, Integer idWaiter);
}
