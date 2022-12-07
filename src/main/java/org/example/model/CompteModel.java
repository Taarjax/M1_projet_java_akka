package org.example.model;

public class CompteModel {
    private long idCompte;
    private long soldeCompte;

    public CompteModel(long idCompte, long soldeCompte) {
        this.idCompte = idCompte;
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
}
