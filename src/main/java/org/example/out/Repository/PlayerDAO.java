package org.example.out.Repository;

import org.example.out.Repository.API.DAO;
import org.example.out.Models.Player;
import org.example.out.Utils.BalanceResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerDAO implements DAO<Player> {
    private static Connection connection;

    @Override
    public Optional<Player> get(Integer id) {
        String sql = "SELECT * FROM entities.player WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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
        String sql = "SELECT * FROM entities.player";
        List<Player> result = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Player player = new Player(
                        resultSet.getInt("id"),
                        new BalanceResult(
                                resultSet.getDouble("balance"),
                                resultSet.getDouble("credit_balance")
                        ),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );

                result.add(player);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public void save(Player player) {
        String sqlSequence = "SELECT nextval('service.my_sequence') as generated_id";

        Long generatedId;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSequence)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            generatedId = resultSet.getLong("generated_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String sql = "INSERT INTO entities.player(id, balance, credit_balance, login, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, generatedId.intValue());
            preparedStatement.setDouble(2, player.getAccounts().getBalance());
            preparedStatement.setDouble(3, player.getAccounts().getCreditBalance());
            preparedStatement.setString(4, player.getLogin());
            preparedStatement.setString(5, player.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Player player) {
        String sql = "UPDATE entities.player SET balance=?, credit_balance=?, login=?, password=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, player.getAccounts().getBalance());
            preparedStatement.setDouble(2, player.getAccounts().getCreditBalance());
            preparedStatement.setString(3, player.getLogin());
            preparedStatement.setString(4, player.getPassword());
            preparedStatement.setInt(5, player.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Updating player failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM entities.player WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        PlayerDAO.connection = connection;
    }
}
