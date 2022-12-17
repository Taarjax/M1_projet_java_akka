package org.example.DAO;

import org.example.model.BanquierModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe permettant de gérer les données des banquiers dans une base de données MySQL.
 *
 * Cette classe hérite de la classe abstraite `DAO` et implémente les méthodes de cette classe pour
 * gérer les données des banquiers dans une base de données MySQL. Elle utilise le driver
 * JDBC de MySQL pour exécuter les requêtes SQL nécessaires à cette gestion.
 *
 * @author  [remy-auloy]
 */
public class BanquierDAO extends DAO<BanquierModel> {

    /**
     * Méthode permettant de créer un banquier dans la base de données MySQL.
     *
     * Cette méthode ne fait rien, car la création de banquiers n'est pas gérer, ils sont déja présent en base de
     * données
     *
     * Cette méthode est présente, car je respecte le pattern DAO et que si l'énoncé du projet voulait
     * qu'on puisse gérer la creation de banquier, ça serait ici qu'on le ferait.
     *
     * @param   obj   le banquier à créer dans la base de données
     * @return  le banquier créé dans la base de données
     */
    @Override
    public BanquierModel create(BanquierModel obj) {
        return null;
    }

    /**
     * Méthode permettant de récupérer un banquier de la base de données MySQL à partir de son identifiant.
     *
     * @param   id    l'identifiant du banquier à récupérer
     * @return  le banquier correspondant à l'identifiant donné, ou un banquier vide s'il n'a pas été trouvé
     */
    @Override
    public BanquierModel get(int id) {
        BanquierModel banquier = new BanquierModel();
        try {
            // Exécution de la requête de récupération du banquier

            ResultSet result = this.connect
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE
                    )
                    .executeQuery("select idBanquier, nom from banquier where idBanquier = " + id);
            // Si le banquier a été trouvé, création d'un objet BanquierModel à partir des données
            // récupérées
            if (result.first()) {
                banquier = new BanquierModel(
                        id,
                        result.getString("nom")
                );
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return banquier;
    }


    /**
     * Méthode permettant de supprimer un banquier de la base de données MySQL.
     *
     * Cette méthode ne fait rien, car je ne gère pas la suppresion de banquier
     * Cette méthode est présente, car je respecte le pattern DAO et que si l'énoncé du projet voulait
     * qu'on puisse gérer la suppression de banquier, ça serait ici qu'on le ferait.
     *
     * @param   obj   le banquier à supprimer de la base de données
     */
    @Override
    public void delete(BanquierModel obj) {

    }

    /**
     * Méthode permettant de mettre à jour les données d'un banquier dans la base de données MySQL.
     *
     * Cette méthode ne fait rien, car je ne gère pas la mise à jour des banquier
     * Cette méthode est présente, car je respecte le pattern DAO et que si l'énoncé du projet voulait
     * qu'on puisse gérer la mise à jour de banquier, ça serait ici qu'on le ferait.
     *
     * @param   ojb   le banquier à mettre à jour dans la base de données
     * @return  le banquier mis à jour dans la base de données
     */
    @Override
    public BanquierModel update(BanquierModel ojb) {
        return null;
    }

    /**
     * Méthode permettant de récupérer tous les banquiers de la base de données MySQL.
     *
     * Cette méthode ne fait rien et retourne null, car la récupération de tous les banquiers est gérée
     * par la classe `BanquierActeur`.
     *
     * @return  la liste de tous les banquiers de la base de données
     */
    @Override
    public List<BanquierModel> getAll() {
        return null;
    }
}
