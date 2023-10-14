package org.example.out.DAO;

import org.example.out.DAO.API.DAO;
import org.example.out.Dispatchers.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PlayerDAO implements DAO<Player> {
    private Connection connection;

    public PlayerDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Player> get(Integer id) {
        String sql = "SELECT * FROM players WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Player> getAll() {
        return null;
    }

    @Override
    public void save(Player player) {

    }

    @Override
    public void update(Player player, String[] params) {

    }

    @Override
    public void delete(Player player) {

    }
}
