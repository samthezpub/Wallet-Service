package org.example.in;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс создан для того чтобы облегчить подключение к бд
 */
public class PropertiesBD {
    private static PropertiesBD INSTANCE;
    private Properties properties = new Properties();


    private PropertiesBD() throws IOException {
            File file = new File("src/main/resources/liquibase.properties");
            FileInputStream in = new FileInputStream(file);
            properties.load(in);

    }

    /**
     * Singleton Lazy, для того чтобы подключиться, ленивая инициализация
     * @return PropertiesDB готовый объект
     */
    public static PropertiesBD getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new PropertiesBD();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return INSTANCE;
    }

    /**
     * Возвращает Login базы данных
     * @return String
     */
    public String getLogin(){
        return properties.get("username").toString();
    }


    /**
     * Возвращает Password базы данных
     * @return String
     */
    public String getPassword(){
        return properties.get("password").toString();
    }

    /**
     * Возвращает JDBC путь базы данных
     * @return String
     */
    public String getDBPath(){
        return properties.get("url").toString();
    }

    public Properties getProperties() {
        return properties;
    }
}
