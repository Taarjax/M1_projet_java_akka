package org.example.DAO;

import org.example.actors.ClientActeur;
import javax.sql.rowset.CachedRowSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


/**
 * Classe DAO (Data Access Object) permettant de gérer les demandes d'un client dans la base de données.
 * Elle hérite de la classe abstraite {@link DAO} et implémente les méthodes abstraites de cette dernière.
 *
 * @author [remy-auloy]
 */


public class DemandeDAO extends DAO<ClientActeur.demandeClient> {
    /**
     * Méthode permettant de créer une nouvelle demande dans la base de données.
     *
     * @param obj la demande à ajouter à la base de données
     * @return la demande ajoutée à la base de données
     */
    @Override
    public ClientActeur.demandeClient create(ClientActeur.demandeClient obj) {
        try {
            PreparedStatement prepare = this.connect.prepareStatement("insert into demande (type,montant,idClient,idCompte) values (?,?,?,?)");

            prepare.setString(1, obj.getDemande());
            prepare.setLong(2, obj.getMontant());
            prepare.setLong(3, obj.getIdClient());
            prepare.setLong(4, obj.getIdCompte());

            // Exécute la requête préparée pour insérer la nouvelle ligne dans la table demande
            prepare.executeUpdate();

            Statement statement = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            // Récupérer l'id de la nouvelle ligne insérée
            ResultSet result = statement.executeQuery("SELECT LAST_INSERT_ID()");

            // Récupérer l'objet demandeClient en utilisant l'ID obtenu
            if (result.first()) {
                long id = result.getLong(1);
                obj = this.get(id);
            }

            prepare.close();
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * Méthode permettant de récupérer une demande à partir de son identifiant dans la base de données.
     *
     * @param id l'identifiant de la demande à récupérer
     * @return la demande correspondant à l'identifiant donné
     */
    @Override
    public ClientActeur.demandeClient get(long id) {
        ClientActeur.demandeClient demande = new ClientActeur.demandeClient();
        try {
            Statement stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt.executeQuery("SELECT * FROM demande where idDemande = " + id);

            if (result.first()) {
                demande = new ClientActeur.demandeClient(result.getLong("idClient"), result.getString("type"), result.getLong("montant"), result.getLong("idCompte"));
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return demande;
    }


    /**
     * Méthode permettant de supprimer une demande de la base de données.
     *
     * @param obj la demande à supprimer de la base de données
     */
    public void delete(ClientActeur.demandeClient obj) {
    }


    /**
     * Méthode permettant de mettre à jour une demande dans la base de données.
     *
     * @param ojb la demande à mettre à jour dans la base de données
     * @return la demande mise à jour dans la base de données
     */
    @Override
    public ClientActeur.demandeClient update(ClientActeur.demandeClient ojb) {
        return null;
    }

    /**
     * Méthode permettant de récupérer toutes les demandes enregistrées dans la base de données.
     *
     * @return la liste de toutes les demandes enregistrées dans la base de données
     */
    @Override
    public List<ClientActeur.demandeClient> getAll() {
        return null;
    }
}
