package org.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connexionMYSQL {
    static {
        //Chargé JDBC
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("oracle.jdbc.driver.OracleDriver");
        }
    }

    private static String url = "jdbc:mysql://localhost:3306/banquev2";
    private static String user = "root";
    private static String password = "root";
    private static Connection connect;

    public static Connection getInstance() {
        if (connect == null) {
            //Se connecter à la base de donnée
            try {
                connect = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connect;
    }
}
