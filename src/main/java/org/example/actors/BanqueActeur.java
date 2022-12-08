package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.pattern.Patterns;
import org.example.model.BanquierModel;
import org.example.model.CompteModel;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.CompletionStage;

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

        //Pour chaque banquier, on lui demande s'il gère le compte de la demande



        for(BanquierModel banquier : listeBanquier){
//            System.out.println("id banquier : "  + banquier.getIdBanquier() + "s'occupe des comptes : "
//                    + banquier.getListeCompteParBanquier());
            CompletionStage<Object> demandeBanqueVersBanquier = Patterns.ask(banquier.getReferenceActeurBanquier(),
                    new BanquierActeur.demandeBanqueVersBanquier(demande, idClient, montant), Duration.ofSeconds(10));
            try {
                String réponse = (String) demandeBanqueVersBanquier.toCompletableFuture().get();
                System.out.println(réponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Réponse de la banque aux clients
        getSender().tell("Réponse de la banque vers le client", getSender());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(demandeBanque.class, message -> demandeAuxBanquier(message.idClient, message.demande, message.montant))
                .build();
    }


}

