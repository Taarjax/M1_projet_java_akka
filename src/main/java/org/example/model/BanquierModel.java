package org.example.model;
import akka.actor.ActorRef;

public class BanquierModel {
    // Attribut
    private int idBanquier;
    private ActorRef ReferenceActeurBanquier;

    public BanquierModel(int _idBanquier, ActorRef _referenceActeurBanquier) {
        this.idBanquier = _idBanquier;
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
}
