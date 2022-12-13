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
        long montant;
        final long idClient;
        final long idCompte;

        public demandeClient(long idClient, String demande, long montant, long idCompte) {
            this.idClient = idClient;
            this.demande = demande;
            this.montant = montant;
            this.idCompte = idCompte;
        }
    }


    //    ----------------------FIN-MESSAGE-------------------------------

    public void demandeAlabanque(long idClient, String _demande, long montant, long idCompte) {

        //Je demande a la banque
        CompletionStage<Object> demande = Patterns.ask(banque.getRefActeurBanque(),
                new BanqueActeur.demandeBanque(idClient, _demande, montant, idCompte), Duration.ofSeconds(10));
        try {
            String reponseDeLaBanque = (String) demande.toCompletableFuture().get();
            System.out.println("Client " + idClient +" : Retour de la banque reçu ! ");
            System.out.println("Réponse en question : " + reponseDeLaBanque);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void DemandeAlaBanqueTell(long idClient, String _demande, long montant, long idCompte) {
        System.out.println("Le client : " + idClient + " demande un : " + _demande + " de " + montant + " € sur le compte : " + idCompte);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(demandeClient.class, message -> demandeAlabanque(message.idClient, message.demande, message.montant, message.idCompte)).
                build();
    }


}
