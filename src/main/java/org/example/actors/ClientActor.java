package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import org.example.model.ClientModel;

/**
 * Le client peut :
 * - déposer de l'argent sur son compte
 * - retirer de l'argent de son compte
 * - Obtenir les informations de son compte
 */
public class ClientActor extends AbstractActor {
    // Référence au model Client
    ClientModel client;

//    ------------------------DEBUT-MESSAGE-----------------------------

//    ----------------------FIN-MESSAGE-------------------------------

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }

}
