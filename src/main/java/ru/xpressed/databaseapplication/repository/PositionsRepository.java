package ru.xpressed.databaseapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.xpressed.databaseapplication.entity.Positions;
import ru.xpressed.databaseapplication.idclass.PositionsID;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PositionsRepository extends JpaRepository<Positions, PositionsID> {
    @Query(value = "SELECT * FROM Positions", nativeQuery = true)
    List<Positions> findAll();

    @Query(value = "SELECT * FROM Positions WHERE (:idMenu is null or ID_Menu = :idMenu) AND" +
            "(:idDish is null or ID_Dish = :idDish)", nativeQuery = true)
    List<Positions> find(Integer idMenu, Integer idDish);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Positions (ID_Menu, ID_Dish) VALUES (:idMenu, :idDish)", nativeQuery = true)
    void insert(int idMenu, int idDish);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Positions WHERE (:idMenu is null or ID_Menu = :idMenu) AND" +
            "(:idDish is null or ID_Dish = :idDish)", nativeQuery = true)
    void delete(Integer idMenu, Integer idDish);
}
