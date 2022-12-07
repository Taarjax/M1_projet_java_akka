package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import org.example.model.BanquierModel;
import org.example.model.CompteModel;

import java.util.ArrayList;

/**
 * Le banquier peut :
 *   - accepter le dépot du client
 *   - refuser le dépot du client
 *   - accepter le retrait du client
 *   - refuser le retrait du client
 *
 */
public class BanquierActeur extends AbstractActor {

    ArrayList<CompteModel> listeCompte;
    public BanquierActeur(){

    }

    public static Props props(ArrayList<BanquierModel> _listeBanquier) {
        return Props.create(BanqueActeur.class, _listeBanquier);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                //Dès qu'on recoit la demande de la banque de vérifier le compte du client
                .build();
    }
}
