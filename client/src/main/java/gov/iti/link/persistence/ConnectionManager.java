package gov.iti.link.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static ConnectionManager instance;
    private Connection connection;

    private ConnectionManager() {

        try {
            Properties props = new Properties();
            System.out.println("URL >> " + getClass().getResource("/db/db.properties").getPath());
            props.load(new FileInputStream(getClass().getResource("/db/db.properties").getPath()));
            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionManager getInstance() {
        try {
            if (instance == null || instance.connection.isClosed()) {
                synchronized (ConnectionManager.class) {
                    instance = new ConnectionManager();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public void close() {
        if (this.connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    
}
