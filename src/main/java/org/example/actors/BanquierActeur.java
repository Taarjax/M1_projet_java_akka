package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import org.example.model.CompteModel;

import java.util.ArrayList;

/**
 * Le banquier peut :
 * - accepter le dépot du client
 * - refuser le dépot du client
 * - accepter le retrait du client
 * - refuser le retrait du client
 */
public class BanquierActeur extends AbstractActor {

    ArrayList<CompteModel> listeCompteParBanquier;

    public BanquierActeur(ArrayList<CompteModel> _listeCompte) {
        this.listeCompteParBanquier = _listeCompte;
    }

    public static Props props(ArrayList<CompteModel> _listeCompteParBanquier) {
        return Props.create(BanquierActeur.class, _listeCompteParBanquier);
    }

    public interface Message {
    }

    public static class demandeBanqueVersBanquier implements Message {
        final String demande;
        final long montant;
        final long idClient;
        final long idBanquier;
        final long idCompte;

        public demandeBanqueVersBanquier(long idClient, String demande, long montant, long idBanquier, long idCompte) {
            this.idClient = idClient;
            this.demande = demande;
            this.montant = montant;
            this.idBanquier = idBanquier;
            this.idCompte = idCompte;
        }
    }


    private void verificationDemande(String demande, long idClient, long montant, long idBanquier, long idCompte) {
        String reponse = "bon compte";
        //Parcourir tous les comptes du banquier
        // Par exemple, le client 1 souhaite faire une demande sur son compte 1
        //Dans ce cas this.listeCompteParBanquier contiendra tous les compte du banquier 1 car c'est lui qui s'occupe
        // du compte 1

        getSender().tell(reponse, getSender());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                //Dès qu'on recoit la demande de la banque de vérifier le compte du client
                .match(demandeBanqueVersBanquier.class, message -> verificationDemande(message.demande, message.idClient, message.montant, message.idBanquier, message.idCompte))
                .build();
    }
}
