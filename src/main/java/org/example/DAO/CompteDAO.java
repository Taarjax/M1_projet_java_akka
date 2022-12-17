package org.example.DAO;

import org.example.model.CompteModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de gérer les données des comptes bancaires dans une base de données MySQL.
 *
 * Cette classe hérite de la classe abstraite `DAO` et implémente les méthodes de cette classe pour
 * gérer les données des comptes bancaires dans une base de données MySQL. Elle utilise le driver
 * JDBC de MySQL pour exécuter les requêtes SQL nécessaires à cette gestion.
 *
 * @author  [remy-auloy]
 */
public class CompteDAO extends DAO<CompteModel> {

    /**
     * Méthode permettant de créer un nouveau compte bancaire dans la base de données MySQL.
     *
     * Cette méthode ne fait rien, car la création de comptes bancaires est gérée par la classe
     * `ClientActeur`. Et que je pars du principe qu'on ne peut pas créer de compte bancaire
     * Dans notre cas, les comptes sont déja stocker en base de données
     * Cette méthode est présente, car je respecte le pattern DAO et que si l'énnoncé du projet voulait
     * qu'on puisse gérer la création et la suppression de compte alors c'est ici qu'on gérerait la création
     *
     * @param   obj   le compte bancaire à créer dans la base de données
     * @return  le compte bancaire créé dans la base de données (null dans ce cas)
     */
    @Override
    public CompteModel create(CompteModel obj) {
        return null;
    }

    /**
     * Méthode permettant de récupérer un compte bancaire dans la base de données MySQL à partir de
     * son identifiant unique.
     *
     * @param   id    l'identifiant unique du compte bancaire à récupérer
     * @return  le compte bancaire récupéré dans la base de données, ou null si aucun compte ne
     *          possède cet identifiant
     */
    @Override
    public CompteModel get(int id) {
        CompteModel compte = new CompteModel();
        try {
            // Exécution de la requête pour récupérer les données du compte bancaire avec l'identifiant spécifié
            ResultSet result = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(
                    "select idCompte, solde, idBanquier, idClient from compte where idCompte = " + id
            );
            // Si un compte a été trouvé, création d'un objet CompteModel avec ses données
            if (result.first()) {
                compte = new CompteModel(
                        id,
                        result.getInt("idClient"),
                        result.getInt("idBanquier"),
                        result.getInt("solde")
                );
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return compte;
    }

    /**
     * Méthode permettant de récupérer tous les comptes bancaires de la base de données MySQL.
     *
     * @return  une liste contenant tous les comptes bancaires de la base de données
     */
    public List<CompteModel> getAll() {
        List<CompteModel> comptes = new ArrayList<>();

        try {
            // Exécution de la requête pour récupérer tous les comptes bancaires de la base de données
            ResultSet result = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(
                    "select idCompte, solde, idBanquier, idClient from compte"
            );
            // Parcourir tous les enregistrements du résultat de la requête et ajouter chaque compte à la liste à retourner
            while (result.next()) {
                int idCompte = result.getInt("idCompte");
                int idClient = result.getInt("idClient");
                int idBanquier = result.getInt("idBanquier");
                int solde = result.getInt("solde");
                CompteModel compte = new CompteModel(idCompte, idClient, idBanquier, solde);
                comptes.add(compte);
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comptes;
    }

    /**
     * Méthode permettant de mettre à jour les données d'un compte bancaire dans la base de données
     * MySQL.
     *
     * Cette méthode met à jour le solde du compte bancaire spécifié dans la base de données.
     *
     * @param   obj   le compte bancaire à mettre à jour dans la base de données
     * @return  le compte bancaire mis à jour dans la base de données
     */
    @Override
    public CompteModel update(CompteModel obj) {
        try {
            String sql = "UPDATE compte SET solde = ? WHERE idCompte = ?";
            PreparedStatement statement = this.connect.prepareStatement(sql);
            statement.setLong(1, obj.getSoldeCompte());
            statement.setLong(2, obj.getIdCompte());
            statement.executeUpdate();

            // Récupération du compte bancaire mis à jour depuis la base de données
            obj = this.get(obj.getIdCompte());
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }


    /**
     * Méthode permettant de supprimer un compte bancaire de la base de données MySQL.
     *
     * Cette méthode ne fait rien, car je ne gère pas la suppresion de compte.
     * Cette méthode est présente, car je respecte le pattern DAO et que si l'énnoncé du projet voulait
     * qu'on puisse gérer la création et la suppression de compte alors c'est ici qu'on gérerait la suppression
     *
     * @param   obj   le compte bancaire à supprimer de la base de données
     */
    @Override
    public void delete(CompteModel obj) {

    }

}
