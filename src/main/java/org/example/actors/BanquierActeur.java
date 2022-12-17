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
 * Classe représentant un acteur banquier.
 *
 * Cette classe hérite de la classe abstraite `AbstractActor` et implémente la méthode `createReceive`
 * pour définir le comportement de l'acteur en réponse aux messages envoyés à celui-ci. Elle possède
 * également une méthode `verificationDemande` qui vérifie la validité d'une demande de retrait ou de dépôt
 * faite par un client et met à jour le solde du compte en conséquence.
 *
 * @author [remy-auloy]
 */
public class BanquierActeur extends AbstractActor {

    /**
     * Liste des comptes gérés par le banquier.
     */
    private ArrayList<CompteModel> listeCompteParBanquier;

    /**
     * Constructeur de la classe.
     *
     * @param _listeCompte liste des comptes gérés par le banquier
     */
    public BanquierActeur(ArrayList<CompteModel> _listeCompte) {
        this.listeCompteParBanquier = _listeCompte;
    }

    /**
     * Méthode statique permettant de créer une configuration de déploiement d'un acteur `BanquierActeur`.
     *
     * @param _listeCompteParBanquier liste des comptes gérés par le banquier
     * @return configuration de déploiement de l'acteur
     */
    public static Props props(ArrayList<CompteModel> _listeCompteParBanquier) {
        return Props.create(BanquierActeur.class, _listeCompteParBanquier);
    }

    /**
     * Interface définissant un message pouvant être envoyé à l'acteur BanquierActeur.
     */
    public interface Message {
    }

    /**
     * Classe représentant une demande de retrait ou de dépôt faite par un client à l'acteur banque et transmise à l'acteur banquier.
     */
    public static class demandeBanqueVersBanquier implements Message {
        private final String demande; // type de demande ("dépôt", "retrait")
        private final int montant; // montant de la demande
        private final int idClient; // identifiant du client qui fait la demande
        private final int idCompte; // identifiant du compte sur lequel la demande doit être effectuée
        private final int idBanquier; // identifiant du banquier qui doit traiter la demande

        /**
         * Constructeur de la classe.
         *
         * @param demande    type de demande ("dépôt", "retrait")
         * @param idClient   identifiant du client qui fait la demande
         * @param montant    montant de la demande
         * @param idCompte   identifiant du compte sur lequel la demande doit être effectuée
         * @param idBanquier identifiant du banquier qui doit traiter la demande
         */
        public demandeBanqueVersBanquier(int idClient, String demande, int montant, int idCompte, int idBanquier) {
            this.idClient = idClient;
            this.demande = demande;
            this.montant = montant;
            this.idCompte = idCompte;
            this.idBanquier = idBanquier;
        }
    }


    /**
     * Vérifie la validité d'une demande de retrait ou de dépôt faite par un client et met à jour le solde du compte en conséquence.
     * Si la demande est valide, elle est enregistrée dans la base de données et le solde du compte est mis à jour.
     * Si la demande est invalide (par exemple en cas de retrait supérieur au solde du compte), un message d'erreur est envoyé à l'acteur banque.
     * qui renvera ce message au client
     *
     * @param demande    type de demande ("dépôt", "retrait")
     * @param idClient   identifiant du client qui fait la demande
     * @param montant    montant de la demande
     * @param idCompte   identifiant du compte sur lequel la demande doit être effectuée
     * @param idBanquier identifiant du banquier qui doit traiter la demande
     */
    public void verificationDemande(String demande, int idClient, int montant, int idCompte, int idBanquier) {
        System.out.println("Banquier " + idBanquier + ": Interrogation de la banque à propos du client " + idClient + " reçu ! ");

        // On utilise des objets DAO pour accéder à la base de données
        DAO<ClientActeur.demandeClient> demandeDAO = DAOFactory.getDemandeDAO();
        DAO<CompteModel> compteDAO = new CompteDAO();
        ClientActeur.demandeClient demandeBDD = new ClientActeur.demandeClient(idClient, demande, montant, idCompte);
        CompteModel compte = null;

        // On parcourt tous les comptes du banquier
        for (CompteModel compteDansBanque : this.listeCompteParBanquier) {
            //On sélectionne le compte demandé par le client
            // En effet, un client peut avoir plusieurs comptes
            //Dès qu'il est trouvé on sort de la boucle
            if (compteDansBanque.getIdCompte() == idCompte && compteDansBanque.getIdClient() == idClient) {
                compte = compteDansBanque;
                break;
            }
        }

        if (compte != null) {
            if (demande.equals("retrait")) {
                if (compte.getSoldeCompte() < montant) {
                    // Si la demande est un retrait et que le solde du compte est insuffisant, on envoie un message d'erreur à l'acteur banque
                    System.out.println("Banquier " + idBanquier + ": Demande accepté");
                    getSender().tell("Impossible de réaliser votre demande, vous souhaitez retirer " + montant + " € alors que vous disposez de " + compte.getSoldeCompte() + " €", getSelf());
                } else {
                    // Si la demande est un retrait et que le solde du compte est suffisant, on effectue la transaction
                    System.out.println("Banquier " + idBanquier + ": Demande accepté");

                    // On met à jour le solde du compte et on enregistre la demande dans la base de données
                    demandeDAO.create(demandeBDD);
                    compte.setSoldeCompte(compte.getSoldeCompte() - montant);
                    CompteModel compte_modifie = new CompteModel(compte.getIdCompte(), compte.getIdClient(), compte.getIdBanquier(), compte.getSoldeCompte());
                    compteDAO.update(compte_modifie);
                    getSender().tell("Vous avez à present : " + compte.getSoldeCompte() + " € sur votre compte n°" + compte.getIdCompte(), getSelf());

                }
            } else if (demande.equals("dépot")) {
                // Si la demande est un dépôt, on effectue la transaction
                System.out.println("Banquier " + idBanquier + ": Demande accepté");
                // On met à jour le solde du compte et on enregistre la demande dans la base de données
                demandeDAO.create(demandeBDD);
                compte.setSoldeCompte(compte.getSoldeCompte() + montant);
                CompteModel compte_modifie = new CompteModel(compte.getIdCompte(), compte.getIdClient(), compte.getIdBanquier(), compte.getSoldeCompte());
                compteDAO.update(compte_modifie);

                getSender().tell("Vous avez à present : " + compte.getSoldeCompte() + " € sur votre compte n°" + compte.getIdCompte(), getSelf());
            } else {
                // Si la demande est incorrecte, on envoie un message d'erreur à l'acteur banque
                getSender().tell("Demande incorrecte", getSelf());
            }
        } else {
            // Si le compte n'existe pas, on envoie un message d'erreur à l'acteur banque
            getSender().tell("Le compte auquel vous souhaitez accéder n'existe pas", getSelf());
        }
    }


    /**
     * Définit le comportement de l'acteur en réponse aux messages envoyés à celui-ci.
     *
     * @return le comportement de l'acteur en réponse aux messages envoyés à celui-ci
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                //Dès qu'on recoit la demande de la banque de vérifier le compte du client
                .match(demandeBanqueVersBanquier.class, message -> verificationDemande(message.demande, message.idClient, message.montant, message.idCompte, message.idBanquier))
                .build();
    }
}
