package org.example.DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Classe permettant d'établir une connexion à une base de données MySQL.
 *
 * Cette classe utilise le driver JDBC de MySQL pour établir une connexion à la base de données.
 * Elle utilise également le design pattern "singleton" pour s'assurer qu'il n'y a qu'une seule
 * connexion active à la base de données à tout moment.
 *
 * @author  [remy-auloy]
 */
public class connexionMYSQL {
    /**
     * Chargement du driver JDBC de MySQL.
     */
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("oracle.jdbc.driver.OracleDriver");
        }
    }

    /**
     * URL de la base de données MySQL.
     */
    private static String url = "jdbc:mysql://localhost:3306/banquev2";
    /**
     * Nom d'utilisateur de la base de données MySQL.
     */
    private static String user = "root";
    /**
     * Mot de passe de la base de données MySQL.
     */
    private static String password = "root";
    /**
     * Objet de connexion à la base de données MySQL.
     */
    private static Connection connect;

    /**
     * Méthode permettant de récupérer l'unique instance de la connexion à la base de données MySQL.
     *
     * Cette méthode utilise le design pattern "singleton" pour s'assurer qu'il n'y a qu'une seule
     * connexion active à la base de données à tout moment. Si aucune connexion n'a été établie
     * jusqu'à présent, cette méthode en crée une nouvelle en utilisant les paramètres de connexion
     * spécifiés dans la classe.
     *
     * @return  l'unique instance de la connexion à la base de données MySQL
     */
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
}
