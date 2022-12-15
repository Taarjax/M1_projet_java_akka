package org.example.model;
import akka.actor.ActorRef;

import java.util.ArrayList;

public class BanquierModel {
    // Attribut
    private long idBanquier;

    private String nom;
    private ActorRef ReferenceActeurBanquier;

    private ArrayList<CompteModel> listeCompteParBanquier;


    public BanquierModel() {}

    public BanquierModel(long idBanquier, String nom) {
        this.idBanquier = idBanquier;
        this.nom = nom;
    }

    public BanquierModel(long _idBanquier, String nom, ArrayList<CompteModel> _listeCompteParBanquier, ActorRef _referenceActeurBanquier) {
        this.idBanquier = _idBanquier;
        this.nom = nom;
        this.listeCompteParBanquier = _listeCompteParBanquier;
        this.ReferenceActeurBanquier = _referenceActeurBanquier;
    }


//    GETTER ET SETTER
    public long getIdBanquier() {
        return idBanquier;
    }

    public ActorRef getReferenceActeurBanquier() {
        return ReferenceActeurBanquier;
    }

    public ArrayList<CompteModel> getListeCompteParBanquier() {
        return listeCompteParBanquier;
    }

    @Override
    public String toString() {
        return "BANQUIER: " +this.idBanquier + " | NOM: "+this.nom + " gère " + listeCompteParBanquier.toString();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setReferenceActeurBanquier(ActorRef ReferenceActeurBanquier) {
        this.ReferenceActeurBanquier = ReferenceActeurBanquier;
    }

    public void setListeCompteParBanquier(ArrayList<CompteModel> listeCompteParBanquier) {
        this.listeCompteParBanquier = listeCompteParBanquier;
    }


}
