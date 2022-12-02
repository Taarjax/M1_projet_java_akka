package org.example.actors;

import akka.actor.AbstractActor;

/**
 * Le banquier peut :
 *   - accepter le dépot du client
 *   - refuser le dépot du client
 *   - accepter le retrait du client
 *   - refuser le retrait du client
 *
 */
public class BanquierActeur extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                //Dès qu'on recoit la demande de la banque de vérifier le compte du client
                .build();
    }
}
