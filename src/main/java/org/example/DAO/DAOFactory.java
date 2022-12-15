package org.example.DAO;

import org.example.actors.ClientActeur;
import org.example.model.BanquierModel;
import org.example.model.ClientModel;
import org.example.model.CompteModel;

public class DAOFactory {

    public static DAO<CompteModel> getCompteDAO() {
        return new CompteDAO();
    }

    public static DAO<BanquierModel> getBanquierDAO() {
        return new BanquierDAO();
    }

    public static DAO<ClientModel> getClientDAO() {
        return new ClientDAO();
    }

    public static DAO<ClientActeur.demandeClient> getDemandeDAO() {
        return new DemandeDAO();
    }

}
