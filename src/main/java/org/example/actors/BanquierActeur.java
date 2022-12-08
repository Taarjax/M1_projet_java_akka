package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import org.example.model.BanquierModel;
import org.example.model.CompteModel;

import java.util.ArrayList;

/**
 * Le banquier peut :
 *   - accepter le dépot du client
 *   - refuser le dépot du client
 *   - accepter le retrait du client
 *   - refuser le retrait du client
 *
 */
public class BanquierActeur extends AbstractActor {

    ArrayList<CompteModel> listeCompte;
    public BanquierActeur(ArrayList<CompteModel> _listeCompte){
        this.listeCompte = _listeCompte;
    }

    public static Props props(ArrayList<CompteModel> _listeComptes) {
        return Props.create(BanquierActeur.class, _listeComptes);
    }

    public interface Message{}
    public static class demandeBanqueVersBanquier implements Message{
        final String demande;
        final long montant;
        final long idClient;

        public demandeBanqueVersBanquier(String demande, long montant, long idClient) {
            this.demande = demande;
            this.montant = montant;
            this.idClient = idClient;
        }
    }

        private void verificationDemande(String demande, long idClient, long montant){

            System.out.println("Compte : " + this.listeCompte);

            for(CompteModel compte : this.listeCompte) {
            }

            getSender().tell("Réponse des banquiers vers la banque", getSender());
        }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                //Dès qu'on recoit la demande de la banque de vérifier le compte du client
                .match(demandeBanqueVersBanquier.class, message -> verificationDemande(message.demande, message.idClient, message.montant))
                .build();
    }
}
