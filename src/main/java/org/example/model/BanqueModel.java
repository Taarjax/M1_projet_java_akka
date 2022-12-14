package org.example.model;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.pattern.Patterns;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class BanqueModel {
    private long idBanque;
    private ActorRef refActeurBanque;

    public BanqueModel(long _idBanque, ActorRef _refActeurBanque) {
        this.idBanque = _idBanque;
        this.refActeurBanque = _refActeurBanque;
    }

//    GETTER ET SETTER
    public ActorRef getRefActeurBanque() {
        return refActeurBanque;
    }

}
