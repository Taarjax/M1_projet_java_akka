package org.example.DAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
               //creationScript();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connect;
    }

//    public static void creationScript() {
//        String script = "";
//        try {
//            Statement stmt = connect.createStatement();
//
//            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/org/example/DAO/script.sql"));
//
//            String line;
//            while ((line = reader.readLine()) != null){
//                script += line + "\n";
//            }
//            reader.close();
//
//            stmt.execute(script);
//            stmt.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
