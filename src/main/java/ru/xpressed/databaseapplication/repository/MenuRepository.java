package ru.xpressed.databaseapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.xpressed.databaseapplication.entity.Menu;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query(value = "SELECT * FROM Menu", nativeQuery = true)
    List<Menu> findAll();

    @Query(value = "SELECT * FROM Menu WHERE ID_Menu = :id", nativeQuery = true)
    Menu find(int id);

    @Query(value = "SELECT * FROM Menu WHERE (:type is null or Type = :type)", nativeQuery = true)
    List<Menu> find(String type);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Menu (Type) VALUES (:type)", nativeQuery = true)
    void insert(String type);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Menu WHERE ID_Menu = :id", nativeQuery = true)
    void delete(int id);
}
