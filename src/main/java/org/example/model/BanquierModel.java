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


}
