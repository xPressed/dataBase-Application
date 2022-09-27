package ru.xpressed.databaseapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.xpressed.databaseapplication.entity.Dish;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Query(value = "SELECT * FROM Dish", nativeQuery = true)
    List<Dish> findAll();

    @Query(value = "SELECT * FROM Dish WHERE ID_Dish = :id", nativeQuery = true)
    Dish find(int id);

    @Query(value = "SELECT * FROM Dish WHERE (:type is null or Type = :type) AND" +
            "(:calories is null or Calories = :calories) AND" +
            "(:compound is null or Compound = :compound) AND" +
            "(:price is null or Price = :price)", nativeQuery = true)
    List<Dish> find(String type, Integer calories, String compound, Integer price);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Dish (Type, Calories, Compound, Price) VALUES (:type, :calories, :compound, :price)", nativeQuery = true)
    void insert(String type, int calories, String compound, int price);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Dish WHERE ID_Dish = :id", nativeQuery = true)
    void delete(int id);
}
