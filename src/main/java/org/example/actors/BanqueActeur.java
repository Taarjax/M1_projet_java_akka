package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import org.example.model.BanqueModel;
import org.example.model.BanquierModel;

import java.util.ArrayList;

public class BanqueActeur extends AbstractActor {

    //Attributs
    private final ArrayList<BanquierModel> listeBanquier;

    public BanqueActeur(ArrayList<BanquierModel> _listeBanquier){
        this.listeBanquier = _listeBanquier;
    }

    public static Props props(ArrayList<BanquierModel> _listeBanquier) {
        return Props.create(BanqueActeur.class, _listeBanquier);
    }




    //    ------------------------DEBUT-MESSAGE-----------------------------

    public interface Message {
    }

    public static class demandeBanque implements Message {
        final String demande;
        final long montant;
        final long idClient;

        public demandeBanque(long idClient, String demande, long montant) {
            this.idClient = idClient;
            this.demande = demande;
            this.montant = montant;
        }
    }


    //    ----------------------FIN-MESSAGE-------------------------------

    private void demandeAuxBanquier(long idClient, String demande, long montant) {
//        if (demande == "dépot") {
//            System.out.println("CLIENT " + idClient + " | Demande d'un " + demande + " de " + montant);
//        }
//        else if (demande == "retrait") {
//            System.out.println("CLIENT " + idClient + " | Demande d'un " + demande + " de " + montant);
//        }

        //ICI on attend la réponse des banquiers pour savoir si c'est possible ou non

        getSender().tell("Possible", getSender());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(demandeBanque.class, message -> demandeAuxBanquier(message.idClient, message.demande, message.montant))
                .build();
    }

    public ArrayList getListeBanquier() {
        return listeBanquier;
    }


}

