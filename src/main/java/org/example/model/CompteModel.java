package org.example.model;

public class CompteModel {
    private long idClient;
    private long idCompte;
    private long idBanquier;
    private long soldeCompte;

    //Constructeur utile pour la db
    public CompteModel(long idClient, long idBanquier, long soldeCompte){
        this.idClient = idClient;
        this.idBanquier = idBanquier;
        this.soldeCompte = soldeCompte;
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
        return "CLIENT : " + idClient + " | COMPTE : " + idCompte ;
    }

    public long getIdClient() {
        return idClient;
    }

    public long getIdBanquier() {
        return idBanquier;
    }

}
