package org.example.model;
import akka.actor.ActorRef;

public class BankerModel {
    // Attribut
    private int idBanker;
    private ActorRef actorRefBanker;

    public BankerModel(int idBanker, ActorRef actorRefBanker) {
        this.idBanker = idBanker;
        this.actorRefBanker = actorRefBanker;
    }

    public ActorRef getActorRefBanker() {
        return actorRefBanker;
    }

    public int getIdBanker() {
        return idBanker;
    }
}
