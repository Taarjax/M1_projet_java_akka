package org.example.model;

public class CompteModel {
    private long idClient;
    private long idCompte;
    private long soldeCompte;

    public CompteModel(long idCompte, long idClient, long soldeCompte) {
        this.idCompte = idCompte;
        this.idClient = idClient;
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
        return Long.toString(idCompte);
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }
}
