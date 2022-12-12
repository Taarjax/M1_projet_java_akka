package org.example.model;
import akka.actor.ActorRef;

import java.util.ArrayList;

public class BanquierModel {
    // Attribut
    private int idBanquier;
    private ActorRef ReferenceActeurBanquier;

    private ArrayList<CompteModel> listeCompteParBanquier;

    public BanquierModel(int _idBanquier, ArrayList<CompteModel> _listeCompteParBanquier, ActorRef _referenceActeurBanquier) {
        this.idBanquier = _idBanquier;
        this.listeCompteParBanquier = _listeCompteParBanquier;
        this.ReferenceActeurBanquier = _referenceActeurBanquier;
    }


//    GETTER ET SETTER
    public int getIdBanquier() {
        return idBanquier;
    }

    public void setIdBanquier(int idBanquier) {
        this.idBanquier = idBanquier;
    }

    public ActorRef getReferenceActeurBanquier() {
        return ReferenceActeurBanquier;
    }

    public void setReferenceActeurBanquier(ActorRef referenceActeurBanquier) {
        ReferenceActeurBanquier = referenceActeurBanquier;
    }

    public ArrayList<CompteModel> getListeCompteParBanquier() {
        return listeCompteParBanquier;
    }

    public void setListeCompteParBanquier(ArrayList<CompteModel> listeCompteParBanquier) {
        this.listeCompteParBanquier = listeCompteParBanquier;
    }

    @Override
    public String toString() {
        return "BANQUIER : " +this.idBanquier + " |" + listeCompteParBanquier.toString();
    }

    //    public ArrayList<BanquierModel> getBanquier

}
