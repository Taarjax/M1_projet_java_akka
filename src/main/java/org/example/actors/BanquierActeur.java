package org.example.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import org.example.DAO.CompteDAO;
import org.example.DAO.DAO;
import org.example.DAO.DAOFactory;
import org.example.model.CompteModel;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Le banquier peut :
 * - accepter le dépot du client
 * - refuser le dépot du client
 * - accepter le retrait du client
 * - refuser le retrait du client
 */
public class BanquierActeur extends AbstractActor {

    private ArrayList<CompteModel> listeCompteParBanquier;

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
        final long idCompte;
        final long idBanquier;

        public demandeBanqueVersBanquier(long idClient, String demande, long montant, long idCompte, long idBanquier) {
            this.idClient = idClient;
            this.demande = demande;
            this.montant = montant;
            this.idCompte = idCompte;
            this.idBanquier = idBanquier;
        }
    }


    private void verificationDemande(String demande, long idClient, long montant, long idCompte, long idBanquier) {
        System.out.println("Banquier " + idBanquier + ": Interrogation de la banque à propos du client " + idClient + " reçu ! ");
        //On parcourt tous les comptes que le banquier du client gère

        DAO<ClientActeur.demandeClient> demandeDAO = DAOFactory.getDemandeDAO();
        DAO<CompteModel> compteDAO = new CompteDAO();
        ClientActeur.demandeClient demandeBDD = new ClientActeur.demandeClient(idClient, demande, montant, idCompte);


        for (CompteModel compte : this.listeCompteParBanquier) {
            //On sélectionne le compte demandé par le client
            // En effet, un client peut avoir plusieurs comptes
            if (compte.getIdCompte() == idCompte && compte.getIdClient() == idClient) {
                if (demande == "retrait") {
                    if (compte.getSoldeCompte() < montant) {
                        System.out.println("Banquier " + idBanquier + ": Demande accepté");
                        getSender().tell("Impossible de réaliser votre demande, vous souhaitez retirer " + montant + " € alors que vous disposez de " + compte.getSoldeCompte() + " €", getSelf());
                    } else {
                        System.out.println("Banquier " + idBanquier + ": Demande accepté");
                        demandeDAO.create(demandeBDD);

                        compte.setSoldeCompte(compte.getSoldeCompte() - montant);
                        CompteModel compte_modifie = new CompteModel(compte.getIdCompte(), compte.getIdClient(),compte.getIdBanquier(),compte.getSoldeCompte());
                        compteDAO.update(compte_modifie);

                        getSender().tell("Vous avez à present : " + compte.getSoldeCompte() + " € sur votre compte n°" + compte.getIdCompte(), getSelf());

                    }
                } else if (demande == "dépot") {
                    System.out.println("Banquier " + idBanquier + ": Demande accepté");
                    demandeDAO.create(demandeBDD);

                    compte.setSoldeCompte(compte.getSoldeCompte() + montant);
                    CompteModel compte_modifie = new CompteModel(compte.getIdCompte(), compte.getIdClient(),compte.getIdBanquier(),compte.getSoldeCompte());
                    compteDAO.update(compte_modifie);
                    getSender().tell("Vous avez à present : " + compte.getSoldeCompte() + " € sur votre compte n°" + compte.getIdCompte(), getSelf());
                }
            }
        }
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                //Dès qu'on recoit la demande de la banque de vérifier le compte du client
                .match(demandeBanqueVersBanquier.class, message -> verificationDemande(message.demande, message.idClient, message.montant, message.idCompte, message.idBanquier))
                .build();
    }
}
