package org.example.actors;

import akka.actor.AbstractActor;
import org.example.model.BanquierModel;

/**
 * La banque peut :
 * - Créer un compte pour un client
 */
public class BanqueActeur extends AbstractActor {

//  Attributs
    BanquierModel referenceBanquier
    @Override
    public Receive createReceive() {
        return null;
    }
}

