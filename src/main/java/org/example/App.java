package org.example;

import org.example.in.Menu.Menu;
import org.example.in.PropertiesBD;
import org.example.out.DAO.PlayerDAO;
import org.example.out.Dispatchers.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Счётчик потраченных часов: 8
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Connection conn;
        try {
             conn = DriverManager.getConnection(
                    PropertiesBD.getInstance().getDBPath(),
                    PropertiesBD.getInstance().getLogin(),
                    PropertiesBD.getInstance().getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PlayerDAO.setConnection(conn);
        PlayerDAO playerDAO = new PlayerDAO();

        Player player = playerDAO.get(0).get();
        player.setLogin("2021423");
        playerDAO.delete(0);


        Menu.menuNotLoggined();
    }
}
