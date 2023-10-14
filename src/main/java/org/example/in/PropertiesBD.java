package org.example.in;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesBD {
    private static PropertiesBD INSTANCE;
    private Properties properties = new Properties();

    private PropertiesBD() throws IOException {
            File file = new File("src/main/resources/db.properties");
            FileInputStream in = new FileInputStream(file);
            properties.load(in);

    }

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

    public String getLogin(){
        return properties.get("login").toString();
    }

    public String getPassword(){
        return properties.get("password").toString();
    }

    public String getDBPath(){
        return properties.get("path").toString();
    }

    public Properties getProperties() {
        return properties;
    }
}
