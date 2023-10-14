package org.example;

import org.example.in.Menu.Menu;
import org.example.in.PropertiesBD;

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
        try {
            Connection conn = DriverManager.getConnection(
                    PropertiesBD.getInstance().getDBPath(),
                    PropertiesBD.getInstance().getLogin(),
                    PropertiesBD.getInstance().getPassword()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        Menu.menuNotLoggined();


    }
}
