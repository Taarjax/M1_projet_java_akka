package org.example.model;

public class CompteModel {
    private long idClient;
    private long idCompte;
    private long idBanquier;
    private long soldeCompte;

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

    public void setIdCompte(long idCompte) {
        this.idCompte = idCompte;
    }

    @Override
    public String toString() {
        return "CLIENT : " + idClient + " | COMPTE : " + idCompte ;
    }


    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    public long getIdBanquier() {
        return idBanquier;
    }

    public void setIdBanquier(long idBanquier) {
        this.idBanquier = idBanquier;
    }


}
