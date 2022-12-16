package org.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.apache.log4j.BasicConfigurator;
import org.example.DAO.BanquierDAO;
import org.example.DAO.DAO;
import org.example.DAO.DAOFactory;
import org.example.actors.BanqueActeur;
import org.example.actors.BanquierActeur;
import org.example.actors.ClientActeur;
import org.example.model.BanqueModel;
import org.example.model.BanquierModel;
import org.example.model.ClientModel;
import org.example.model.CompteModel;

import org.example.DAO.DAO;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainDBO {
    public static String randomDemande() {
        String[] s = new String[]{"retrait", "dépot"};
        Random ran = new Random();
        String s_ran = s[ran.nextInt(s.length)];
        return s_ran;
    }

    public static int randomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Min doit etre plus grand que max");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public static void main(String[] args) throws InterruptedException {


        BasicConfigurator.configure();
        ActorSystem system = ActorSystem.create();
        System.out.println("Application lancé");


        //Creation des comptes
        DAO<CompteModel> compteDAO = DAOFactory.getCompteDAO();
        CompteModel compte1 = compteDAO.get(1);
        CompteModel compte2 = compteDAO.get(2);
        CompteModel compte3 = compteDAO.get(3);
        CompteModel compte4 = compteDAO.get(4);
        CompteModel compte5 = compteDAO.get(5);


//        //Liste des comptes géré par le banquier n°1
        ArrayList<CompteModel> listeComptePourBanquier1 = new ArrayList<>();
        listeComptePourBanquier1.add(compte1);
        listeComptePourBanquier1.add(compte2);

        ArrayList<CompteModel> listeComptePourBanquier2 = new ArrayList<>();
        listeComptePourBanquier2.add(compte3);

        ArrayList<CompteModel> listeComptePourBanquier3 = new ArrayList<>();
        listeComptePourBanquier3.add(compte4);
        listeComptePourBanquier3.add(compte5);

        //BANQUIER
        DAO<BanquierModel> banquierDAO = DAOFactory.getBanquierDAO();

        //Banquier1
        BanquierModel banquier1 = banquierDAO.get(1);
        ActorRef acteurBanquier1 = system.actorOf(BanquierActeur.props(listeComptePourBanquier1));
        banquier1.setReferenceActeurBanquier(acteurBanquier1);
        banquier1.setListeCompteParBanquier(listeComptePourBanquier1);

        //Banquier2
        BanquierModel banquier2 = banquierDAO.get(2);
        ActorRef acteurBanquier2 = system.actorOf(BanquierActeur.props(listeComptePourBanquier2));
        banquier2.setReferenceActeurBanquier(acteurBanquier2);
        banquier2.setListeCompteParBanquier(listeComptePourBanquier2);

        //Banquier3
        BanquierModel banquier3 = banquierDAO.get(3);
        ActorRef acteurBanquier3 = system.actorOf(BanquierActeur.props(listeComptePourBanquier3));
        banquier3.setReferenceActeurBanquier(acteurBanquier3);
        banquier3.setListeCompteParBanquier(listeComptePourBanquier3);


        //Liste des banquiers
        ArrayList<BanquierModel> listeBanquier = new ArrayList<>();
        listeBanquier.add(banquier1);
        listeBanquier.add(banquier2);
        listeBanquier.add(banquier3);


        //Banque
        ActorRef actorBanque = system.actorOf(BanqueActeur.props(listeBanquier));
        BanqueModel banque = new BanqueModel(1, actorBanque);


        //Client n°1
//        DAO<ClientModel> clientDAO = DAOFactory.getClientDAO();
        //ClientModel client1 = clientDAO.get(1);
        //ActorRef actorClient1 = system.actorOf(ClientActeur.props(banque));
        //client1.setRefActeurClient(actorClient1);
//
//        //Client n°2
        //ClientModel client2 = clientDAO.get(2);
        //  ActorRef actorClient2 = system.actorOf(ClientActeur.props(banque));
        //    client2.setRefActeurClient(actorClient2);
//
//        //Client n°3
//        ClientModel client3 = clientDAO.get(3);
//        ActorRef actorClient3 = system.actorOf(ClientActeur.props(banque));
//        client3.setRefActeurClient(actorClient3);
//
//
//        //Demande du client 1
//        ClientActeur.demandeClient demande1 = new ClientActeur.demandeClient(client1.getIdClient(), randomDemande(), randomNumber(0, 500), 1);
//        ClientActeur.demandeClient demande2 = new ClientActeur.demandeClient(client2.getIdClient(), randomDemande(), randomNumber(0, 500), 2);
//        ClientActeur.demandeClient demande3 = new ClientActeur.demandeClient(client3.getIdClient(), randomDemande(), randomNumber(0, 500), 4);

        DAO<ClientModel> clientDAO = DAOFactory.getClientDAO();
        int NB_CLIENTS = clientDAO.getAll().size();
        int nombreMaximumDemandes = 50;
        Random random = new Random();

        for (int i = 1; i <= NB_CLIENTS; i++) {
            // Récupération du client à l'aide de la DAO de clients
            ClientModel client = clientDAO.get(i);

            // Création d'un acteur ClientActeur pour le client
            ActorRef actorClient = system.actorOf(ClientActeur.props(banque));
            // Enregistrement de l'acteur du client dans l'objet client
            client.setRefActeurClient(actorClient);

            // Récupération de la liste de tous les comptes du client
            List<CompteModel> comptes = client.getComptesAllComptes();

            // Si le client possède au moins un compte
            if (!comptes.isEmpty()) {
                // Boucle sur le nombre maximum de demandes que le client peut faire à la suite
                for (int j = 0; j < nombreMaximumDemandes; j++) {
                    // Génération d'une valeur aléatoire comprise entre 0 et 1 avec une probabilité de 0,5 de faire une demande
                    if (random.nextDouble() < 0.5) {
                        // Choix au hasard d'un compte parmi la liste des comptes du client
                        int indexCompte = random.nextInt(comptes.size());
                        CompteModel compte = comptes.get(indexCompte);

                        // Création d'un objet demandeClient avec des valeurs aléatoires pour l'identifiant du client, le montant de la demande et l'identifiant du compte
                        ClientActeur.demandeClient demande = new ClientActeur.demandeClient(client.getIdClient(), randomDemande(), randomNumber(0, 500), compte.getIdCompte());
                        // Envoi de la demande au client
                        client.lancement(demande);
                    }
                }
            }
        }
        Thread.sleep(500);
        system.terminate();
    }

}
