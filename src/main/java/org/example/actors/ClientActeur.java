package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.pattern.Patterns;
import org.example.model.BanqueModel;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

/**
 * Le client peut :
 * - déposer de l'argent sur son compte
 * - retirer de l'argent de son compte
 * - Obtenir les informations de son compte
 */
public class ClientActeur extends AbstractActor {

    private final BanqueModel banque;

    public ClientActeur(BanqueModel _banque) {
        this.banque = _banque;
    }

    public static Props props(BanqueModel _banque) {
        return Props.create(ClientActeur.class, _banque);
    }

//    ------------------------DEBUT-MESSAGE-----------------------------

    public interface Message {
    }

    public static class demandeClient implements Message {
        final String demande;
        final long montant;
        final long idClient;

        public demandeClient(long idClient, String demande, long montant) {
            this.idClient = idClient;
            this.demande = demande;
            this.montant = montant;
        }
    }


    //    ----------------------FIN-MESSAGE-------------------------------

    public void demandeAlabanque(long idClient, String _demande, long montant) {

        //Je demande a la banque
        CompletionStage<Object> demande = Patterns.ask(banque.getRefActeurBanque(),
                new BanqueActeur.demandeBanque(idClient, _demande, montant), Duration.ofSeconds(10));
        try {
            String isPossible = (String) demande.toCompletableFuture().get();
            System.out.println(isPossible);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(demandeClient.class, message -> demandeAlabanque(message.idClient, message.demande, message.montant)).
                build();
    }


}
