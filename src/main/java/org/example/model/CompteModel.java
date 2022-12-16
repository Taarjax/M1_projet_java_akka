package org.example.model;

import org.example.DAO.DAO;
import org.example.DAO.DAOFactory;


/**
 * Représente un compte bancaire.
 *
 * @author [remy-auloy]
 */
public class CompteModel {

    private long idClient; // L'identifiant du client associé à ce compte.
    private long idCompte; // L'identifiant de ce compte.
    private long idBanquier; // L'identifiant du banquier responsable de ce compte.
    private long soldeCompte; // Le solde actuel de ce compte.

    /**
     * Constructeur par défaut.
     */
    public CompteModel() {
    }


    /**
     * Constructeur avec initialisation des champs.
     *
     * @param idCompte L'identifiant de ce compte.
     * @param idClient L'identifiant du client associé à ce compte.
     * @param idBanquier L'identifiant du banquier responsable de ce compte.
     * @param soldeCompte Le solde actuel de ce compte.
     */
    public CompteModel(long idCompte, long idClient, long idBanquier, long soldeCompte) {
        this.idCompte = idCompte;
        this.idClient = idClient;
        this.idBanquier = idBanquier;
        this.soldeCompte = soldeCompte;
    }

    /**
     * Retourne le solde actuel de ce compte.
     *
     * @return Le solde actuel de ce compte.
     */
    public long getSoldeCompte() {
        return soldeCompte;
    }

    /**
     * Modifie le solde actuel de ce compte.
     *
     * @param soldeCompte Le nouveau solde de ce compte.
     */
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
