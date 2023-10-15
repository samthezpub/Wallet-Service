package org.example.out.Repository;

import org.example.out.Repository.API.DAO;
import org.example.out.Dispatchers.Transaction;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TransactionDAO implements DAO<Transaction> {

    private static Connection connection;
    @Override
    public Optional<Transaction> get(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Transaction> getAll() {
        return null;
    }


    public Set<Transaction> findTransactionsByPlayerId(Integer id) {
        return null;
    }

    @Override
    public void save(Transaction transaction) {
        String sqlSequence = "SELECT nextval('entities.transaction_sequence') as generated_id";

        Long generatedId;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSequence)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            generatedId = resultSet.getLong("generated_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String sql = "INSERT INTO entities.transaction(id, player_id, type, balanceBefore, creditBefore, balanceAfter, creditAfter, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, generatedId.intValue());
            preparedStatement.setInt(2, transaction.getPlayerId());
            preparedStatement.setInt(3, typeId);
            preparedStatement.setDouble(4, transaction.getAccounts().getBalance());
            preparedStatement.setDouble(5, transaction.getAccounts().getCreditBalance());
            preparedStatement.setDouble(6, transaction.getBalanceAfterOperation().getBalance());
            preparedStatement.setDouble(7, transaction.getBalanceAfterOperation().getCreditBalance());

            Date date = new Date(transaction.getDate().getTime());
            preparedStatement.setDate(8, date);

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


}
