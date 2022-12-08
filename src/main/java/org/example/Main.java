package org.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.apache.log4j.BasicConfigurator;
import org.example.actors.BanqueActeur;
import org.example.actors.BanquierActeur;
import org.example.actors.ClientActeur;
import org.example.model.BanqueModel;
import org.example.model.BanquierModel;
import org.example.model.ClientModel;
import org.example.model.CompteModel;

import java.util.*;


public class Main {

    public static String randomDemande() {
        String[] s = new String[]{"retrait", "dépot"};
        Random ran = new Random();
        String s_ran = s[ran.nextInt(s.length)];
        return s_ran;
    }

    private static int randomNumber(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("Min doit etre plus grand que max");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        ActorSystem system = ActorSystem.create();

        System.out.println("Application lancé");

//      Création des comptes (MODEL)
        CompteModel compte1 = new CompteModel( 1, 1,2000);
        CompteModel compte2 = new CompteModel( 2, 2,4000);
        CompteModel compte3 = new CompteModel( 3, 1,2000);
        CompteModel compte4 = new CompteModel( 4, 3,2000);
        CompteModel compte5 = new CompteModel( 5, 4,2000);
        CompteModel compte6 = new CompteModel( 6, 5,2000);

//      Liste des comptes géré par le banquier
//      Banquier1
        ArrayList<CompteModel> listeComptePourBanquier1= new ArrayList<>();
        listeComptePourBanquier1.add(compte1);
        listeComptePourBanquier1.add(compte2);
        listeComptePourBanquier1.add(compte5);

        //Banquier 2
        ArrayList<CompteModel> listeComptePourBanquier2= new ArrayList<>();
        listeComptePourBanquier2.add(compte3);
        listeComptePourBanquier2.add(compte4);
        listeComptePourBanquier2.add(compte6);


//      Création des banquiers utilisants la liste des comptes
        ActorRef acteurBanquier1 = system.actorOf(BanquierActeur.props(listeComptePourBanquier1));
        BanquierModel banquier1 = new BanquierModel(1, listeComptePourBanquier1,acteurBanquier1);

        ActorRef acteurBanquier2 = system.actorOf(BanquierActeur.props(listeComptePourBanquier2));
        BanquierModel banquier2 = new BanquierModel(2,listeComptePourBanquier2 ,acteurBanquier2);

//      Ajout des banquiers dans une liste (utile pour la banque)
        ArrayList<BanquierModel> listeBanquier = new ArrayList<>();
        listeBanquier.add(banquier1);
        listeBanquier.add(banquier2);

//      Création de la banque
        ActorRef actorBanque = system.actorOf(BanqueActeur.props(listeBanquier));
        BanqueModel banque = new BanqueModel(1, actorBanque);

//      Création des clients avec leur compte correspondant
        ActorRef actorClient = system.actorOf(ClientActeur.props(banque));

//      On remarque que le client 1 à 2 comptes
        ArrayList<CompteModel> comptesDuClient1 = new ArrayList<>();
        ArrayList<CompteModel> comptesDuClient2 = new ArrayList<>();
        comptesDuClient1.add(compte1);
        comptesDuClient1.add(compte3);
        comptesDuClient2.add(compte2);


        ClientModel client1 = new ClientModel(1, comptesDuClient1, actorClient);
        ClientModel client2 = new ClientModel(2, comptesDuClient2, actorClient);

        //Création d'une liste de client, pratique pour faire une boucle de demande
        ArrayList<ClientModel> listeClient = new ArrayList<>();
        listeClient.add(client1);
//        listeClient.add(client2);

        System.out.println("Taille de la liste de client : " + listeClient.size());
//        ICI SONT LANCÉ LES DEMANDES DES CLIENTS ALÉATOIREMENT
        for (int i = 0; i <= listeClient.size() - 1; i++) {
            ClientModel client = listeClient.get(i);
            client.lancement(client.getIdClient(), randomDemande(), randomNumber(0, 200));
//            System.out.println("Client n° " + client.getIdClient() + " à pour compte le compte n° :  " + client.getCompte());
        }


        system.terminate();
    }
}