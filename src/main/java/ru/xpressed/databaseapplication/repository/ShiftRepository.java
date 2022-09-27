package ru.xpressed.databaseapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.xpressed.databaseapplication.entity.Shift;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    @Query(value = "SELECT * FROM Shift", nativeQuery = true)
    List<Shift> findAll();

    @Query(value = "SELECT * FROM Shift WHERE ID_Shift = :id", nativeQuery = true)
    Shift find(int id);

    @Query(value = "SELECT * FROM Shift WHERE (:type is null or Type = :type) AND" +
            "(:timetable is null or Timetable = :timetable)", nativeQuery = true)
    List<Shift> find(String type, String timetable);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Shift (Type, Timetable) VALUES (:type, :timetable)", nativeQuery = true)
    void insert(String type, String timetable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Shift WHERE ID_Shift = :id", nativeQuery = true)
    void delete(int id);
}
