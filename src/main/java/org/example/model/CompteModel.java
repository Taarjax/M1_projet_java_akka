package org.example.model;



/**
 * Représente un compte bancaire.
 *
 * @author [remy-auloy]
 */
public class CompteModel {

    private int idClient; // L'identifiant du client associé à ce compte.
    private int idCompte; // L'identifiant de ce compte.
    private int idBanquier; // L'identifiant du banquier responsable de ce compte.
    private int soldeCompte; // Le solde actuel de ce compte.

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
    public CompteModel(int idCompte, int idClient, int idBanquier, int soldeCompte) {
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
    public int getSoldeCompte() {
        return soldeCompte;
    }

    /**
     * Modifie le solde actuel de ce compte.
     *
     * @param soldeCompte Le nouveau solde de ce compte.
     */
    public void setSoldeCompte(int soldeCompte) {
        this.soldeCompte = soldeCompte;
    }

    /**
     * Retourne l'identifiant de ce compte.
     *
     * @return L'identifiant de ce compte.
     */
    public int getIdCompte() {
        return idCompte;
    }

    /**
     * Retourne une représentation en chaîne de caractères de ce compte.
     *
     * @return Une représentation en chaîne de caractères de ce compte.
     */
    @Override
    public String toString() {
        return "Compte n°" + idCompte + " | CLIENT : " + idClient + " | SOLDE : " + this.soldeCompte;
    }

    /**
     * Retourne l'identifiant du client associé à ce compte.
     *
     * @return L'identifiant du client associé à ce compte.
     */
    public int getIdClient() {
        return idClient;
    }

    /**
     * Retourne l'identifiant du banquier responsable de ce compte.
     *
     * @return L'identifiant du banquier responsable de ce compte.
     */
    public int getIdBanquier() {
        return idBanquier;
    }

}
