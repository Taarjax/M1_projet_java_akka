package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * La banque peut :
 * - Créer un compte pour un client
 */
public class Bank extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    final long bankId;

    public Bank(long _bankId) {

        this.bankId = _bankId;
    }

    public static Props props(long _bankId) {
        return Props.create(Bank.class, _bankId);
    }

    //    ------------------------DEBUT-MESSAGE-----------------------------
    public static class CreateAccount {
        final long accountId;

        public CreateAccount(long accountId) {
            this.accountId = accountId;
        }
    }


//    ----------------------FIN-MESSAGE-------------------------------

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Client.Deposit.class, deposit -> {
                    log.info("Demande de dépot du client d'un montant de {}", deposit.amount);

                })
                .match(CreateAccount.class, createAccount -> {
                    System.out.println("Création du compte");

                })
                .build();
    }
}
