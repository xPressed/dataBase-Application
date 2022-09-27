package ru.xpressed.databaseapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.xpressed.databaseapplication.entity.Order;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * FROM `Order`", nativeQuery = true)
    List<Order> findAll();

    @Query(value = "SELECT * FROM `Order` WHERE (:id is null or ID_Order = :id)", nativeQuery = true)
    Order find(int id);

    @Query(value = "SELECT * FROM `Order` WHERE (:idOrder is null or ID_Order = :idOrder) AND" +
            "(:idDish is null or ID_Dish = :idDish)", nativeQuery = true)
    List<Order> find(Integer idOrder, Integer idDish);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `Order` (ID_Dish)" +
            "VALUES (:idDish)", nativeQuery = true)
    void insert(int idDish);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `Order` WHERE (:id is null or ID_Order = :id)", nativeQuery = true)
    void delete(int id);
}
