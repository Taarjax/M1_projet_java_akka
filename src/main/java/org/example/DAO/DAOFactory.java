package org.example.DAO;

import org.example.actors.ClientActeur;
import org.example.model.BanquierModel;
import org.example.model.ClientModel;
import org.example.model.CompteModel;


/**
 * Classe permettant de récupérer les différents DAO utilisés dans l'application.
 *
 * Cette classe contient plusieurs méthodes statiques permettant de récupérer les DAO correspondant
 * aux différents modèles utilisés dans l'application.
 *
 * @author  [remy-auloy
 */
public class DAOFactory {

    /**
     * Méthode permettant de récupérer le DAO permettant de gérer les comptes.
     *
     * @return  le DAO permettant de gérer les comptes
     */
    public static DAO<CompteModel> getCompteDAO() {
        return new CompteDAO();
    }

    /**
     * Méthode permettant de récupérer le DAO permettant de gérer les banquiers.
     *
     * @return  le DAO permettant de gérer les banquiers
     */
    public static DAO<BanquierModel> getBanquierDAO() {
        return new BanquierDAO();
    }

    /**
     * Méthode permettant de récupérer le DAO permettant de gérer les clients.
     *
     * @return  le DAO permettant de gérer les clients
     */
    public static DAO<ClientModel> getClientDAO() {
        return new ClientDAO();
    }

    /**
     * Méthode permettant de récupérer le DAO permettant de gérer les demandes de clients.
     *
     * @return  le DAO permettant de gérer les demandes de clients
     */
    public static DAO<ClientActeur.demandeClient> getDemandeDAO() {
        return new DemandeDAO();
    }

}
