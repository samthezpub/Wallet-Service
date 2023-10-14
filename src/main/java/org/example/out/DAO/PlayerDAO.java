package org.example.out.DAO;

import org.example.out.DAO.API.DAO;
import org.example.out.Dispatchers.Player;
import org.example.out.Utils.BalanceResult;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String sql = "SELECT * FROM entities.player WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return Optional.of(
                    new Player(
                        resultSet.getInt("id"),
                        new BalanceResult(
                                resultSet.getDouble("balance"),
                                resultSet.getDouble("credit_balance")
                                ),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                    )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
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
