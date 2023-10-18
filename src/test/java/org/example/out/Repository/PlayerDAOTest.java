package org.example.out.Repository;



import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.changelog.ChangeLogParameters;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import org.example.out.Models.Player;
import org.example.out.Models.Transaction;
import org.example.out.Utils.BalanceResult;
import org.junit.jupiter.api.*;
import org.postgresql.jdbc.PgConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.images.builder.Transferable;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Testcontainers
public class PlayerDAOTest {

    private static Connection connection = null;
    private static PlayerDAO playerDAO;

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

        playerDAO = new PlayerDAO();


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


        PlayerDAO.setConnection(connection);

        playerDAO = new PlayerDAO();
    }

    @Test
    public void testConnection_mustReturnOne(){
        ResultSet resultSet = null;
        try {
            resultSet = connection.prepareStatement("Select 1").executeQuery();
            resultSet.next();
            int actual = resultSet.getInt(1);
            Assertions.assertEquals(1, actual);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void getById_mustReturnOptionalPlayer__callable() {

        Player expected = new Player(1, new BalanceResult(0,0),"login", "password");
        playerDAO.save(expected);

        Player actual = playerDAO.get(1).get();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAll_mustReturnListPlayer__callable(){
        List<Player> expected = new ArrayList<>();
        expected.add(new Player(1, new BalanceResult(0,0), "1", "1"));
        expected.add(new Player(2, new BalanceResult(0,0), "2", "2"));
        expected.add(new Player(3, new BalanceResult(0,0), "3", "3"));

        playerDAO.save(new Player(1, new BalanceResult(0,0), "1", "1"));
        playerDAO.save(new Player(2, new BalanceResult(0,0), "2", "2"));
        playerDAO.save(new Player(3, new BalanceResult(0,0), "3", "3"));

        Assertions.assertEquals(expected, playerDAO.getAll());
    }

    @Test
    public void save_mustAddNewPlayer__callable(){
        Player expected = new Player(1, new BalanceResult(0,0), "1", "1");
        playerDAO.save(expected);

        Assertions.assertEquals(expected, playerDAO.get(1).get());
    }

    @Test
    public void update_mustUpdateExistPlayer__callable(){
        Player expected = new Player(1, new BalanceResult(0,0), "1", "1");
        playerDAO.save(expected);

        Player updatedExpected = new Player(1, new BalanceResult(2,0), "1", "1");
        playerDAO.update(updatedExpected);

        Assertions.assertEquals(updatedExpected, playerDAO.get(1));
    }

    @Test
    public void delete_mustDeleteExistPlayer__callable(){
        Player expected = new Player(1, new BalanceResult(0,0), "1", "1");
        playerDAO.save(expected);

        playerDAO.delete(1);

        Assertions.assertTrue(playerDAO.get(1).isEmpty());
    }
}
