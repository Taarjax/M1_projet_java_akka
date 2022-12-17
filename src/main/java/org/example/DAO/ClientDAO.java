package org.example.DAO;


import org.example.model.ClientModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe permettant de gérer les données des clients dans une base de données MySQL.
 *
 * Cette classe hérite de la classe abstraite `DAO` et implémente les méthodes de cette classe pour
 * gérer les données des clients dans une base de données MySQL. Elle utilise le driver
 * JDBC de MySQL pour exécuter les requêtes SQL nécessaires à cette gestion.
 *
 * @author  [remy-auloy]
 */
public class ClientDAO  extends DAO<ClientModel> {
    /**
     * Méthode permettant d'ajouter un client dans la base de données MySQL.
     *
     * Cette méthode ajoute un nouveau client dans la base de données en utilisant l'identifiant
     * auto-incrémenté de la table `client` et en utilisant les données du client spécifié.
     * Cette méthode n'est en réalité pas utilisée dans mon projet, mais je l'ai codé car j'ai cru
     * qu'à un moment elle me serait utile, même s'il n'était pas demandé de pouvoir créer des clients
     * dans le "cahier des charges".
     *
     * @param   obj   le client à ajouter dans la base de données
     * @return  le client ajouté dans la base de données
     */
    @Override
    public ClientModel create(ClientModel obj) {
        try {
            //Récupération de la prochaine valeur de l'auto-incrément de idClient
            ResultSet result = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(
                    "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'client'"
            );
            if (result.first()) {
                int id = result.getInt("AUTO_INCREMENT");
                PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO client (idClient, nom) VALUES (?,?)");
                prepare.setLong(1, id);
                prepare.setString(2, obj.getNom());
                prepare.executeUpdate();

                // Récupération du client ajouté depuis la base de données
                obj = this.get(id);
                prepare.close();
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }


    /**
     * Méthode permettant de récupérer un client à partir de son identifiant unique dans la base de
     * données MySQL.
     *
     * @param   id   l'identifiant unique du client à récupérer
     * @return  le client correspondant à l'identifiant spécifié
     */
    @Override
    public ClientModel get(int id) {
        ClientModel client = new ClientModel();
        try {
            // Exécution de la requête de récupération du client à partir de son identifiant
            ResultSet result = this.connect
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE
                    )
                    .executeQuery(
                            "SELECT idClient, nom FROM client where idClient = " + id
                    );
            if (result.first()) {
                // Création d'un nouveau client à partir des données récupérées de la base de données
                client = new ClientModel(
                        id,
                        result.getString("nom")
                );
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    /**
     * Méthode permettant de supprimer un client de la base de données MySQL.
     *
     * Cette méthode ne fait rien, car je ne gère pas la suppresion de client.
     * Cette méthode est présente, car je respecte le pattern DAO et que si l'énnoncé du projet voulait
     * qu'on puisse gérer la suppression de client alors c'est ici qu'on la gérerait
     *
     * @param   obj   le client à supprimer de la base de données
     */
    @Override
    public void delete(ClientModel obj) {

    }

    /**
     * Méthode permettant de mettre à jour les données d'un client dans la base de données MySQL.
     *
     * Cette méthode ne fait rien, car je ne gère pas la mise à jour des clients.
     * Cette méthode est présente, car je respecte le pattern DAO et que si l'énnoncé du projet voulait
     * qu'on puisse gérer la mise à jour d'un client alors c'est ici qu'on la gérerait.
     *
     * @param   ojb   le client à mettre à jour dans la base de données
     * @return  le client mis à jour dans la base de données
     */
    @Override
    public ClientModel update(ClientModel ojb) {
        return null;
    }

    /**
     * Méthode permettant de récupérer tous les clients de la base de données MySQL.
     *
     * @return  une liste de tous les clients de la base de données
     */
    @Override
    public List<ClientModel> getAll() {
        List<ClientModel> clients = new ArrayList<>();

        try {
            // Exécution de la requête de récupération de tous les clients
            ResultSet result = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(
                    "select * from Client"
            );

            // Parcours des enregistrements du résultat de la requête et ajout de chaque client à la liste
            // à retourner
            while (result.next()){
                int idClient = result.getInt("idClient");
                String nom = result.getString("nom");
                ClientModel client = new ClientModel(idClient, nom);

                clients.add(client);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clients;
    }
}
