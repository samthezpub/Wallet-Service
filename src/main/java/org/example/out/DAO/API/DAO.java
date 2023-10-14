package org.example.out.DAO.API;

import org.example.out.Dispatchers.Player;

import java.util.List;
import java.util.Optional;

public interface DAO<T>{
    Optional<T> get(Integer id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void update(Player player);

    void delete(int id);

}
