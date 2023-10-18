package org.example.out.Repository;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import org.example.out.Models.Transaction;
import org.example.out.Utils.BalanceResult;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

public class TransactionDAOTest {
    private static Connection connection = null;
    private static TransactionDAO transactionDAO;

    @Container
    private static PostgreSQLContainer postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("wallet")
            .withUsername("vova")
            .withPassword("101010")
            .withCopyFileToContainer(MountableFile.forHostPath("src/main/resources/db/changelog/db.changelog-master.xml"), "/test/");




    @BeforeAll
    public static void beforeAll() {

        postgres.start();

        try {
            connection = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        PlayerDAO.setConnection(connection);

        transactionDAO = new TransactionDAO();


        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }



    @AfterAll
    public static void afterAll() {
        postgres.stop();
    }


    @BeforeEach
    public void setUp() {

        try {
            connection = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        TransactionDAO.setConnection(connection);

        transactionDAO = new TransactionDAO();
    }

    @Test
    public void findTransactionsByPlayerId_mustReturnSetTransaction__callable(){

        Set<Transaction> expected = new LinkedHashSet<>();
        expected.add(new Transaction(1, 1, new BalanceResult(100, 200), new BalanceResult(200,200)));
        expected.add(new Transaction(1, 1, new BalanceResult(200, 200), new BalanceResult(300,200)));

        Assertions.assertEquals(expected, transactionDAO.findTransactionsByPlayerId(1));
    }

    @Test
    public void save_mustSaveTransaction__callable(){
        Transaction expected = new Transaction(1, 2, new BalanceResult(100, 100), new BalanceResult(200, 100));
        transactionDAO.save(expected);

        Assertions.assertEquals(expected, transactionDAO.get(1));
    }
}
