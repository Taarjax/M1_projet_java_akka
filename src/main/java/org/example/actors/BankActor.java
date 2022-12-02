package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.ArrayList;
import java.util.concurrent.CompletionStage;
import akka.pattern.Patterns;
import org.example.model.BankModel;
import org.example.model.BankerModel;

/**
 * La banque peut :
 * - Créer un compte pour un client
 */
public class BankActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private BankModel refBankModel;
    final ArrayList bankerList = new ArrayList<BankerModel>();

    //Constructeur (on passe via les props)
    public static Props props(long _bankId) {
        return Props.create(BankActor.class, _bankId);
    }

    //    ------------------------DEBUT-MESSAGE-----------------------------

    // Définition des messages en inner classe
    public interface BankMessage {
    }

    public static class CreateAccount implements BankMessage {
        final long accountId;

        public CreateAccount(long accountId) {
            this.accountId = accountId;
        }
    }


//    ----------------------FIN-MESSAGE-------------------------------

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                //Demande du clien de déposer ou retirer
                .match(ClientActor.Deposit.class, deposit -> {
                    log.info("Demande de dépot du client d'un montant de {} €", deposit.amount);
                    //On fait la vrai demande ici
                    //askBanker();
                })
                .match(CreateAccount.class, createAccount -> {
                    System.out.println("Création du compte");

                })
                .build();
    }

    public void askBanker(){
        /*
        On parcourt tous les banquier afin de trouver celui qui s'occupe du client
        Et on lui transmet la demande
        C'est le banquier qui vérifira si oui ou non la demande est possible
         */
//        CompletionStage<Object> demande = Patterns.ask(refBankModel, // );
    }
}

