package org.example.out.Repository;

import org.example.out.Models.TransactionType;
import org.example.out.Repository.API.DAO;

import java.util.List;
import java.util.Optional;

public class TransactionTypeDAO implements DAO<TransactionType> {


    /**
     * @param id
     * @return
     */
    @Override
    public Optional<TransactionType> get(Integer id) {
        String sql = "SELECT * FROM ";

        return Optional.empty();
    }

    /**
     * @return
     */
    @Override
    public List<TransactionType> getAll() {
        return null;
    }

    /**
     * @param transactionType
     */
    @Override
    public void save(TransactionType transactionType) {

    }

    /**
     * @param transactionType
     */
    @Override
    public void update(TransactionType transactionType) {

    }

    /**
     * @param id
     */
    @Override
    public void delete(int id) {

    }
}
