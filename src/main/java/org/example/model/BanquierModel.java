package org.example.model;
import akka.actor.ActorRef;

import java.util.ArrayList;

public class BanquierModel {
    // Attribut
    private long idBanquier;
    private ActorRef ReferenceActeurBanquier;

    private ArrayList<CompteModel> listeCompteParBanquier;


    public BanquierModel( ActorRef _referenceActeurBanquier) {
        this.ReferenceActeurBanquier = _referenceActeurBanquier;
    }

    public BanquierModel(long _idBanquier, ArrayList<CompteModel> _listeCompteParBanquier, ActorRef _referenceActeurBanquier) {
        this.idBanquier = _idBanquier;
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
        return "BANQUIER : " +this.idBanquier + " |" + listeCompteParBanquier.toString();
    }

}
