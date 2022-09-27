package ru.xpressed.databaseapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.xpressed.databaseapplication.entity.Waiter;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Repository
public interface WaiterRepository extends JpaRepository<Waiter, Integer> {
    @Query(value = "SELECT * FROM Waiter", nativeQuery = true)
    List<Waiter> findAll();

    @Query(value = "SELECT * FROM Waiter WHERE ID_Waiter = :id", nativeQuery = true)
    Waiter find(int id);

    @Query(value = "SELECT * FROM Waiter WHERE (:surname is null or Surname = :surname) AND" +
            "(:name is null or Name = :name) AND" +
            "(:patronymic is null or Patronymic = :patronymic) AND" +
            "(:gender is null or Gender = :gender) AND" +
            "(:dateOfBirth is null or Date_Of_Birth = :dateOfBirth) AND" +
            "(:idShift is null or ID_Shift = :idShift)", nativeQuery = true)
    List<Waiter> find(String surname, String name, String patronymic, String gender, Date dateOfBirth, Integer idShift);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Waiter (Surname, Name, Patronymic, Gender, Date_Of_Birth, ID_Shift)" +
            "VALUES (:surname, :name, :patronymic, :gender, :dateOfBirth, :idShift)", nativeQuery = true)
    void insert(String surname, String name, String patronymic, String gender, Date dateOfBirth, int idShift);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Waiter WHERE ID_Waiter = :id", nativeQuery = true)
    void delete(int id);
}
