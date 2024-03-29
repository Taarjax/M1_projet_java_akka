package org.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.apache.log4j.BasicConfigurator;
import org.example.DAO.DAO;
import org.example.DAO.DAOFactory;
import org.example.actors.BanqueActeur;
import org.example.actors.BanquierActeur;
import org.example.actors.ClientActeur;
import org.example.model.BanqueModel;
import org.example.model.BanquierModel;
import org.example.model.ClientModel;
import org.example.model.CompteModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Classe MainDBO de la classe
 * Dans cette fonction nous gérons la liaison avec la base de données
 * Ainsi, le code de cette fonction agira sur la base de donnée
 */
public class MainDBO {

    /**
     * Génère une demande de retrait ou de dépôt de manière aléatoire.
     *
     * @return la demande générée (soit "retrait", soit "dépôt")
     */
    public static String randomDemande() {
        String[] s = new String[]{"retrait", "depot"};
        Random ran = new Random();
        String s_ran = s[ran.nextInt(s.length)];
        return s_ran;
    }

    /**
     * Génère un nombre entier aléatoire compris entre min et max (inclus).
     *
     * @param min le nombre minimum à générer
     * @param max le nombre maximum à générer
     * @return le nombre entier aléatoire généré
     * @throws IllegalArgumentException si min est supérieur ou égal à max
     */
    public static int randomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Min doit etre plus grand que max");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    /**
     * Génère de manière aléatoire des demandes de la part des clients d'une banque.
     *
     * @param system                l'objet Actor system utilisé pour la création des acteurs client
     * @param banque                le modèle de la banque avec laquelle les clients interagissent
     * @param nombreMaximumDemandes le nombre maximum de demandes que chaque client peut faire à la suite
     */
    public static void randomLancement(ActorSystem system, BanqueModel banque, int nombreMaximumDemandes) {
        DAO<ClientModel> clientDAO = DAOFactory.getClientDAO();
        int NB_CLIENTS = clientDAO.getAll().size();
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
    }

    /**
     * Génère trois demandes simples de la part de trois clients spécifiques.
     *
     * @param system l'objet actor system utilisé pour la création des acteurs client
     * @param banque le modèle de la banque avec laquelle les clients interagissent
     */
    public static void lancementSimple(ActorSystem system, BanqueModel banque) throws InterruptedException {

        // Récupération de la liste de tous les clients
        List<ClientModel> clients = DAOFactory.getClientDAO().getAll();

        // Afficher les comptes des clients pour informations
        for (ClientModel client : clients) {
            // Récupération de la liste de tous les comptes du client
            List<CompteModel> comptes = client.getComptesAllComptes();

            // Affichage de la liste de tous les comptes du client
            System.out.println("Le client " + client.getIdClient() + " possède les comptes :");
            for (CompteModel compte : comptes) {
                System.out.println("- Compte " + compte.getIdCompte());
            }
        }

        System.out.println("------------------------------------");

        // Création des instances de ClientActeur pour chaque client
        for (ClientModel client : clients) {
            ActorRef actorClient = system.actorOf(ClientActeur.props(banque));
            client.setRefActeurClient(actorClient);

            // Récupération de la liste de tous les comptes du client
            List<CompteModel> comptes = client.getComptesAllComptes();

            // Envoi d'une demande pour chaque compte du client
            for (CompteModel compte : comptes) {
                ClientActeur.demandeClient demande = new ClientActeur.demandeClient(client.getIdClient(), randomDemande(), randomNumber(0, 500), compte.getIdCompte());
                client.lancement(demande);
            }
        }

        ClientActeur.demandeClient demandeCompteInexistant = new ClientActeur.demandeClient(clients.get(1).getIdClient(), randomDemande(), randomNumber(0, 500), 9);
        ClientActeur.demandeClient demandeCompteNonAutorise = new ClientActeur.demandeClient(clients.get(1).getIdClient(), randomDemande(), randomNumber(0, 500), 4);

        // Envoi de demandes sur un compte qui n'existe pas et sur un compte qui n'est pas autorisé
        clients.get(1).lancement(demandeCompteInexistant);
        clients.get(1).lancement(demandeCompteNonAutorise);

        Thread.sleep(500);
    }


    /**
     * Point d'entrée de l'application de simulation de banque.
     * Crée les acteurs banque, banquiers et clients, ainsi que leurs modèles respectifs, et génère des demandes aléatoires ou simples de la part des clients.
     *
     * @param args les arguments de la ligne de commande (non utilisés ici)
     * @throws InterruptedException si une interruption est détectée pendant la pause de l'application
     */
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


        // Demande à l'utilisateur de choisir entre les deux modes de lancement
        Scanner sc = new Scanner(System.in);
        System.out.println("Choisissez le mode de lancement :");
        System.out.println("1. Lancement simple");
        System.out.println("2. Lancement aléatoire");
        int choix = sc.nextInt();


        int tempsPause = 0;
        if (choix == 1) {
            // Lancement simple
            lancementSimple(system, banque);
        } else if (choix == 2) {
            // Lancement aléatoire
            System.out.println("Choisissez le nombre de demandes maximales par client :");
            int nombreDemandes = sc.nextInt();
            randomLancement(system, banque, nombreDemandes);
            tempsPause = 500;

            if (nombreDemandes > 10) {
                tempsPause = (int) (tempsPause * Math.log(nombreDemandes));
            }
        } else {
            System.out.println("Choix incorrect. Veuillez choisir entre les deux modes de lancement disponibles.");
        }

        sc.close();
        Thread.sleep(tempsPause);
        system.terminate();
    }
}
