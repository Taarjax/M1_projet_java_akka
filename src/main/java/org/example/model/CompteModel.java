package org.example.model;

import org.example.DAO.DAO;
import org.example.DAO.DAOFactory;

public class CompteModel {
    private long idClient;
    private long idCompte;
    private long idBanquier;
    private long soldeCompte;

    public CompteModel() {
    }


    //Constructeur utile lors des test sans DB
    public CompteModel(long idCompte, long idClient, long idBanquier, long soldeCompte) {
        this.idCompte = idCompte;
        this.idClient = idClient;
        this.idBanquier = idBanquier;
        this.soldeCompte = soldeCompte;
    }

    public long getSoldeCompte() {
        return soldeCompte;
    }

    public void setSoldeCompte(long soldeCompte) {
        this.soldeCompte = soldeCompte;
    }

    public long getIdCompte() {
        return idCompte;
    }

    @Override
    public String toString() {
        return "Compte n°" + idCompte + " | CLIENT : " + idClient + " | SOLDE : " + this.soldeCompte;
    }

    public long getIdClient() {
        return idClient;
    }

    public long getIdBanquier() {
        return idBanquier;
    }

}
