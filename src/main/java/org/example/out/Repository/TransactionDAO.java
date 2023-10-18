package org.example.out.Repository;

import org.example.out.Repository.API.DAO;
import org.example.out.Models.Transaction;
import org.example.out.Utils.BalanceResult;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class TransactionDAO implements DAO<Transaction> {

    private static Connection connection;

    @Override
    public Optional<Transaction> get(Integer id) {
        String sql = "SELECT * FROM entities.transaction WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            Transaction transaction = new Transaction(
                    resultSet.getInt("player_id"),
                    resultSet.getInt("type"),
                    new BalanceResult(
                            resultSet.getDouble("balance_before"),
                            resultSet.getDouble("credit_before")
                    ),
                    new BalanceResult(
                            resultSet.getDouble("balance_after"),
                            resultSet.getDouble("credit_after")
                    )
            );

            return Optional.of(transaction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> getAll() {
        return null;
    }


    /**
     * Ищет транзакции с player_id равным аргументу id
     *
     * @param id - id игрока
     * @return Set<Transaction>
     * @see Transaction
     */
    public Set<Transaction> findTransactionsByPlayerId(Integer id) {
        Set<Transaction> userTransactions = new LinkedHashSet<>();
        String sql = "SELECT * FROM entities.transaction WHERE player_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                        resultSet.getInt("player_id"),
                        resultSet.getInt("type"),
                        new BalanceResult(
                                resultSet.getDouble("balance_before"),
                                resultSet.getDouble("credit_before")
                        ),
                        new BalanceResult(
                                resultSet.getDouble("balance_after"),
                                resultSet.getDouble("credit_after")
                        )
                );
                transaction.setId(resultSet.getInt("id"));

                userTransactions.add(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userTransactions;
    }

    /**
     * Сохраняет транзакцию в базе данных
     *
     * @param transaction - модель Transaction
     * @see Transaction
     */
    @Override
    public void save(Transaction transaction) {
        String sqlSequence = "SELECT nextval('entities.transaction_sequence') as generated_id";

        Long generatedId;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSequence)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            generatedId = resultSet.getLong("generated_id");
            transaction.setId(generatedId.intValue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String sql = "INSERT INTO entities.transaction(id, player_id, type, balance_before, credit_before, balance_after, credit_after, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, transaction.getId());
            preparedStatement.setInt(2, transaction.getPlayerId());
            preparedStatement.setInt(3, transaction.getTypeId());
            preparedStatement.setDouble(4, transaction.getAccounts().getBalance());
            preparedStatement.setDouble(5, transaction.getAccounts().getCreditBalance());
            preparedStatement.setDouble(6, transaction.getBalanceAfterOperation().getBalance());
            preparedStatement.setDouble(7, transaction.getBalanceAfterOperation().getCreditBalance());

            Date date = new Date(transaction.getDate().getTime());
            preparedStatement.setDate(8, date);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Transaction transaction) {

    }


    @Override
    public void delete(int id) {

    }

    public static void setConnection(Connection connection) {
        TransactionDAO.connection = connection;
    }
}
