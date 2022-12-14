package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
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
        System.out.println("------------------------------------");
        System.out.println("Banque: Message reçu du client " + idClient);
        String reponseDesBanquiers = "";
        BanquierModel temp_banquier = null;
        boolean estLeBonCompte = false;

        //On cherche le bon compte du client parmis la liste des comptes des banquiers
            for (BanquierModel banquier : this.listeBanquier) {
                for (CompteModel compteDansBanque : banquier.getListeCompteParBanquier()) {
                    if (banquier.getIdBanquier() == compteDansBanque.getIdBanquier() && compteDansBanque.getIdCompte() == idCompte && compteDansBanque.getIdClient() == idClient) {
                        estLeBonCompte = true;
                        temp_banquier = banquier;
                    }
                }
            }

            //Si c'est le bon compte, on effectue la demande
        if (estLeBonCompte) {
            System.out.println("Banque: interroge votre banquier pour savoir si votre demande est possible, il s'agit du banquier "+ temp_banquier.getIdBanquier());
            CompletionStage<Object> demandeBanqueVersBanquier = Patterns.ask(temp_banquier.getReferenceActeurBanquier(),
                    new BanquierActeur.demandeBanqueVersBanquier(idClient, demande, montant, idCompte, temp_banquier.getIdBanquier()), Duration.ofSeconds(10));
            try {
                reponseDesBanquiers = (String) demandeBanqueVersBanquier.toCompletableFuture().get();
                System.out.println("Banque: Retour de la réponse du banquier " + temp_banquier.getIdBanquier() + " concernant le client " + idClient );
                getSender().tell(reponseDesBanquiers, getSelf());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Sinon c'est qu'il y a une erreur
            getSender().tell("Erreur dans la saisi du compte, vous ne posséder pas le compte au quel vous souhaitez accéder", getSelf());
        }
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(demandeBanque.class, message -> demandeAuxBanquier(message.idClient, message.demande, message.montant, message.idCompte))
                .build();
    }


}

