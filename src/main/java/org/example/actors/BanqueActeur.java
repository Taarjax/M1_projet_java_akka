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
import java.util.concurrent.locks.ReentrantLock;


/**
 * La classe BanqueActeur est une classe qui étend la classe AbstractActor de Akka.
 * Elle gère les demandes des clients en interrogeant les banquiers associés à ces clients.
 *
 * @author [remy-auloy]
 */
public class BanqueActeur extends AbstractActor {

    //Attributs
    private final ArrayList<BanquierModel> listeBanquier;

    /**
     * Constructeur de la classe BanqueActeur.
     *
     * @param _listeBanquier une ArrayList de BanquierModel contenant les banquiers associés à cette banque.
     */
    public BanqueActeur(ArrayList<BanquierModel> _listeBanquier) {
        this.listeBanquier = _listeBanquier;
    }

    /**
     * Méthode statique permettant de créer des Props pour l'acteur BanqueActeur.
     *
     * @param _listeBanquier une ArrayList de BanquierModel contenant les banquiers associés à cette banque.
     * @return un objet Props pour la création d'un acteur BanqueActeur.
     */
    public static Props props(ArrayList<BanquierModel> _listeBanquier) {
        return Props.create(BanqueActeur.class, _listeBanquier);
    }


    //    ------------------------DEBUT-MESSAGE-----------------------------

    /**
     * Interface Message définissant les différents types de messages pouvant être reçus par l'acteur BanqueActeur.
     */
    public interface Message {
    }


    /**
     * Classe demandeBanque définissant un message de demande d'une opération bancaire.
     */
    public static class demandeBanque implements Message {
        private final String demande;
        private final int montant;
        private final int idClient;
        private final int idCompte;

        /**
         * Constructeur de la classe demandeBanque.
         *
         * @param idClient l'identifiant du client faisant la demande.
         * @param demande  la demande de l'opération bancaire (dépôt, retrait, etc.).
         * @param montant  le montant de l'opération bancaire.
         * @param idCompte l'identifiant du compte sur lequel l'opération doit être effectuée.
         */
        public demandeBanque(int idClient, String demande, int montant, int idCompte) {
            this.idClient = idClient;
            this.demande = demande;
            this.montant = montant;
            this.idCompte = idCompte;
        }
    }


    //    ----------------------FIN-MESSAGE-------------------------------

    /**
     * Méthode permettant de traiter une demande de l'opération bancaire reçue par l'acteur BanqueActeur.
     * La méthode interroge le banquier associé au client ayant effectué la demande et renvoie la réponse du banquier au client.
     *
     * @param idClient l'identifiant du client faisant la demande.
     * @param demande  la demande de l'opération bancaire (dépôt, retrait, etc.).
     * @param montant  le montant de l'opération bancaire.
     * @param idCompte l'identifiant du compte sur lequel l'opération doit être effectuée.
     */
    public void demandeAuxBanquier(int idClient, String demande, int montant, int idCompte) {
        System.out.println("------------------------------------");
        System.out.println("Banque: Message reçu du client " + idClient);

        String reponseDesBanquiers = "";
        BanquierModel temp_banquier = null;
        boolean estLeBonCompte = false;

        //On cherche le bon compte du client parmis la liste des comptes des banquiers
        for (BanquierModel banquier : this.listeBanquier) {
            for (CompteModel compteDansBanque : banquier.getListeCompteParBanquier()) {
                if (compteDansBanque.getIdCompte() == idCompte && compteDansBanque.getIdClient() == idClient) {
                    estLeBonCompte = true;
                    temp_banquier = banquier;
                    break;
                }
            }
            if (estLeBonCompte) {
                break;
            }
        }
        //Si c'est le bon compte, on effectue la demande
        if (estLeBonCompte) {
            System.out.println("Banque: interroge votre banquier pour savoir si votre demande est possible, il s'agit du banquier " + temp_banquier.getIdBanquier());
            CompletionStage<Object> demandeBanqueVersBanquier = Patterns.ask(temp_banquier.getReferenceActeurBanquier(),
                    new BanquierActeur.demandeBanqueVersBanquier(idClient, demande, montant, idCompte, temp_banquier.getIdBanquier()), Duration.ofSeconds(10));
            try {
                reponseDesBanquiers = (String) demandeBanqueVersBanquier.toCompletableFuture().get();
                System.out.println("Banque: Retour de la réponse du banquier " + temp_banquier.getIdBanquier() + " concernant le client " + idClient);
                getSender().tell(reponseDesBanquiers, getSelf());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Sinon c'est qu'il y a une erreur
            getSender().tell("Erreur dans la saisi du compte, vous ne posséder pas le compte au quel vous souhaitez accéder", getSelf());
        }
    }

    /**
     * Définit le comportement de l'acteur en réponse aux messages envoyés à celui-ci.
     *
     * @return le comportement de l'acteur en réponse aux messages envoyés à celui-ci
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(demandeBanque.class, message -> demandeAuxBanquier(message.idClient, message.demande, message.montant, message.idCompte))
                .build();
    }
}

