package org.example;

import org.example.in.Menu.Menu;
import org.example.in.PropertiesBD;
import org.example.out.Repository.PlayerDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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



        Menu.menuNotLoggined();
    }
}
