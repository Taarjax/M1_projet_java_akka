package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
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


        private void verificationDemande(String demande, long idClient, long montant, long idBanquier, long idCompte){
            String reponse  = "";
            //System.out.println("Compte : " + this.listeCompte);


            for(CompteModel compte : this.listeCompte) {
//                System.out.println(" avant : " + compte.getSoldeCompte());
                //Si le compte de la demande est dans la liste des comptes que s'occupe le banquier
                if (idClient == compte.getIdClient() && idCompte == compte.getIdCompte()) {
                    //Si la demande est un dépot
                    if(demande == "dépot"){
                    }//Sinon retrait
                    else{
                        if(compte.getSoldeCompte() < montant){
                            reponse = "La demande n'est pas possible, vous avez " + compte.getSoldeCompte() + " € disponible sur votre compte " +
                                    "alors que vous souhaitez retirez " + montant + " €";
                        }
                        else{
                            reponse = "La demande est possible. Vous avez " + compte.getSoldeCompte() + " €, et souhaitez retirez " + montant + " €";
//                            compte.setSoldeCompte(compte.getSoldeCompte() - montant);
//                            System.out.println(" après : " + compte.getSoldeCompte());
                        }
                    }
                }
                else if(idClient == compte.getIdClient() && idCompte != compte.getIdCompte()){
                    reponse = "Vous demandez d'accéder au compte : " + idCompte + " qui n'est pas votre compte, vous avez comme compte : " + compte.getIdCompte();
                }

            }
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
