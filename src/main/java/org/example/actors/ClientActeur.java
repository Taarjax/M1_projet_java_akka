package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.pattern.Patterns;
import org.example.model.BanqueModel;

import java.time.Duration;
import java.util.concurrent.CompletionStage;


/**
 * Classe représentant un acteur client dans une application de gestion de banque basée sur Akka.
 *
 * Cette classe hérite de la classe abstraite `AbstractActor` et implémente la méthode `createReceive`
 * pour définir le comportement de l'acteur en réponse aux messages envoyés à celui-ci. Elle possède
 * également une méthode `demandeAlabanque` qui envoie une demande à l'acteur banque et affiche
 * la réponse reçue de la banque une fois que celle ci aura la réponse des banquiers.
 *
 * @author [remy-auloy]
 */
public class ClientActeur extends AbstractActor {

    private final BanqueModel banque;

    /**
     * Constructeur de la classe.
     *
     * @param _banque instance de la classe `BanqueModel` représentant la banque à laquelle le client envoie ses demandes
     */
    public ClientActeur(BanqueModel _banque) {
        this.banque = _banque;
    }

    /**
     * Méthode statique permettant de créer une configuration de déploiement d'un acteur `ClientActeur`.
     *
     * @param _banque instance de la classe `BanqueModel` représentant la banque à laquelle le client envoie ses demandes
     * @return configuration de déploiement de l'acteur
     */
    public static Props props(BanqueModel _banque) {
        return Props.create(ClientActeur.class, _banque);
    }


//    ------------------------DEBUT-MESSAGE-----------------------------

    /**
     Interface définissant un message pouvant être envoyé à l'acteur ClientActeur.
     */
    public interface Message {
    }

    /**
     * Classe représentant une demande d'un client à la banque.
     */
    public static class demandeClient implements Message {
        private String demande;
        private int montant;
        private int idClient;
        private int idCompte;

        /**
         * Constructeur simple de la classe.
         */
        public demandeClient() {
        }

        /**
         * Constructeur de la classe.
         *
         * @param idClient identifiant du client qui fait la demande
         * @param demande type de demande (par exemple "dépôt", "retrait")
         * @param montant montant de la demande
         * @param idCompte identifiant du compte sur lequel la demande doit être effectuée
         */
        public demandeClient(int idClient, String demande, int montant, int idCompte) {
            this.idClient = idClient;
            this.demande = demande;
            this.montant = montant;
            this.idCompte = idCompte;
        }

        //GETTERS

        /**
         * Retourne le type de demande.
         *
         * @return type de demande
         */
        public String getDemande() {
            return this.demande;
        }

        /**
         * Retourne le montant de la demande.
         *
         * @return montant de la demande
         */
        public int getMontant() {
            return this.montant;
        }

        /**
         * Retourne l'identifiant du client qui a fait la demande.
         *
         * @return identifiant du client
         */
        public int getIdClient() {
            return this.idClient;
        }

        /**
         * Retourne l'identifiant du compte sur lequel la demande doit être effectuée.
         *
         * @return identifiant du compte
         */
        public int getIdCompte() {
            return this.idCompte;
        }

        /**
         * Retourne une chaîne de caractères représentant l'objet.
         *
         * @return chaîne de caractères représentant l'objet
         */
        @Override
        public String toString() {
            return "Client n°"+this.idClient+" à demandé un "+this.demande+" de "+this.montant +" sur le compte "+this.idCompte;
        }
    }
    //    ----------------------FIN-MESSAGE-------------------------------



    /**
     * Envoie une demande à l'acteur banque et affiche la réponse reçue.
     *
     * @param idClient identifiant du client qui fait la demande
     * @param _demande type de demande (par exemple "dépôt", "retrait")
     * @param montant montant de la demande
     * @param idCompte identifiant du compte sur lequel la demande doit être effectuée
     */
    public void demandeAlabanque(int idClient, String _demande, int montant, int idCompte) {
        // Envoie de la demande à l'acteur banque en utilisant la méthode 'ask' de Akka
        // et en spécifiant une durée maximale d'attente de 10 secondes
        CompletionStage<Object> demande = Patterns.ask(banque.getRefActeurBanque(),
                new BanqueActeur.demandeBanque(idClient, _demande, montant, idCompte), Duration.ofSeconds(10));

        // Récupération de la réponse de la banque
        try {
            String reponseDeLaBanque = (String) demande.toCompletableFuture().get();
            System.out.println("Client " + idClient + " : Retour de la banque reçu ! ");
            System.out.println("Réponse client " + idClient + " en question : " + reponseDeLaBanque);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Définit le comportement de l'acteur en réponse aux messages envoyés à celui-ci.
     *
     * @return le comportement de l'acteur en réponse aux messages envoyés à celui-ci
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(demandeClient.class, message -> demandeAlabanque(message.idClient, message.demande, message.montant, message.idCompte)).
                build();
    }
}
