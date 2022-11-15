package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * Le client peut :
 * - déposer de l'argent sur son compte
 * - retirer de l'argent de son compte
 * - Obtenir les informations de son compte
 */
public class Client extends AbstractActor {
    // Déclaration des variables
    final long accountId;
    final long userId;
    //Private car on doit y accéder pour modifier le solde
    private long balance; //Solde du client

    // Constructeur de la classe Client
    public Client(long _accountId, long _userId, long _balance) {
        this.accountId = _accountId;
        this.userId = _userId;
        this.balance = _balance;
    }

    // Création d'un acteur Client (équivalent du constructeur pour akka)
    public static Props props(long _accountId, long _userId, long _initialBalance) {
        return Props.create(Client.class, _accountId, _userId, _initialBalance);
    }

//    ------------------------DEBUT-MESSAGE-----------------------------

    // Définition des messages en inner classe

    /**
     * Message permettant au client de déposé de l'argent sur son compte
     */
    public static class Deposit {
        final long amount;

        public Deposit(long _amount) {
            this.amount = _amount;
        }
    }

    /**
     * Message permettant au client de retirer de l'argent de son compte
     */
    public static class Withdrawal {
        final long amount;

        public Withdrawal(long _amount) {
            this.amount = _amount;
        }
    }

    /**
     * Méthode qui mets a jour le solde
     * Méthode appellé lorsque le banquier autorisera ou non la modification
     */
    public static class UpdateSolde {
        final long accountId;
        final long userId;
        final long balance;

        public UpdateSolde(long _accountId, long _userId, long _balance) {
            this.accountId = _accountId;
            this.userId = _userId;
            this.balance = _balance;
        }

    }


//    ----------------------FIN-MESSAGE-------------------------------


    @Override
    public Receive createReceive() {
        return receiveBuilder()
//                Si le client veut déposer de l'argent
//            <=> Si on recoit un message de type Deposit.class
//                .match(Deposit.class, deposit -> {
//                    this.balance += deposit.amount;
//                    //On met a jour le compte avec le nouveau solde
//                })
                .build();
    }


}
