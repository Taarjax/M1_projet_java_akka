package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.pattern.Patterns;
import org.example.model.BanquierModel;
import org.example.model.CompteModel;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletionStage;

public class BanqueActeur extends AbstractActor {

    //Attributs
    private final ArrayList<BanquierModel> listeBanquier;

    public BanqueActeur(ArrayList<BanquierModel> _listeBanquier) {
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
        final long idCompte;

        public demandeBanque(long idClient, String demande, long montant, long idCompte) {
            this.idClient = idClient;
            this.demande = demande;
            this.montant = montant;
            this.idCompte = idCompte;
        }
    }


    //    ----------------------FIN-MESSAGE-------------------------------

    private void demandeAuxBanquier(long idClient, String demande, long montant, long idCompte) {
        //Pour chaque banquier, on lui demande s'il gère le compte de la demande
        System.out.println(listeBanquier.toString());

        String reponseDesBanquiers = "";

        //On cherche le banquier qui s'occupe du compte
        for (BanquierModel banquier : this.listeBanquier) {
            for (CompteModel compteDansBanque : banquier.getListeCompteParBanquier()) {
                //Si tout concorde et qu'en théorie, c'est le bon banquier
                if (banquier.getIdBanquier() == compteDansBanque.getIdBanquier() && compteDansBanque.getIdCompte() == idCompte && compteDansBanque.getIdClient() == idClient) {

                    //Une fois qu'on a le bon banquier, on lui fait la demande
                    CompletionStage<Object> demandeBanqueVersBanquier = Patterns.ask(banquier.getReferenceActeurBanquier(),
                            new BanquierActeur.demandeBanqueVersBanquier(idClient, demande, montant, banquier.getIdBanquier(), idCompte), Duration.ofSeconds(10));
                    try {
                        reponseDesBanquiers = (String) demandeBanqueVersBanquier.toCompletableFuture().get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                // SINON il n'y a pas de banquier associer au compte
            }
        }

        //La banque va répondre au client à partir de la réponse des banquiers.
        getSender().tell(reponseDesBanquiers, getSender());
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(demandeBanque.class, message -> demandeAuxBanquier(message.idClient, message.demande, message.montant, message.idCompte))
                .build();
    }


}

