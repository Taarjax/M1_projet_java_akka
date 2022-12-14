package org.example.DAO;

import org.example.model.BanquierModel;
import org.example.model.CompteModel;

public class DAOFactory {

    public static DAO<CompteModel> getCompteDAO() {
        return new CompteDAO();
    }

    public static DAO<BanquierModel> getBanquierDAO() {
        return new BanquierDAO();
    }
}
