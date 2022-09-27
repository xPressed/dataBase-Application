package ru.xpressed.databaseapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.xpressed.databaseapplication.entity.Client;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query(value = "SELECT * FROM Client", nativeQuery = true)
    List<Client> findAll();

    @Query(value = "SELECT * FROM Client WHERE ID_Client = :id", nativeQuery = true)
    Client find(int id);

    @Query(value = "SELECT * FROM Client WHERE (:idOrder is null or ID_Order = :idOrder) AND" +
            "(:surname is null or Surname = :surname) AND" +
            "(:name is null or Name = :name) AND" +
            "(:patronymic is null or Patronymic = :patronymic)", nativeQuery = true)
    List<Client> find(Integer idOrder, String surname, String name, String patronymic);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Client (ID_Order, Surname, Name, Patronymic)" +
            "VALUES (:idOrder, :surname, :name, :patronymic)", nativeQuery = true)
    void insert(int idOrder, String surname, String name, String patronymic);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Client WHERE ID_Client = :id", nativeQuery = true)
    void delete(int id);
}
