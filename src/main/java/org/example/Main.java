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

import static java.lang.Integer.parseInt;


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
        CompteModel compte1 = new CompteModel( 1, 1,1,300);
        CompteModel compte2 = new CompteModel( 2, 2,1,4000);
        CompteModel compte3 = new CompteModel( 3, 1,2,500);
        CompteModel compte4 = new CompteModel( 4, 3,3,2000);
        CompteModel compte5 = new CompteModel( 5, 3,3,2000);

//      Liste des comptes géré par le banquier
//      Banquier1
        ArrayList<CompteModel> listeComptePourBanquier1= new ArrayList<>();
        listeComptePourBanquier1.add(compte1);
        listeComptePourBanquier1.add(compte2);
//        listeComptePourBanquier1.add(compte5);

        //Banquier 2
        ArrayList<CompteModel> listeComptePourBanquier2= new ArrayList<>();
        listeComptePourBanquier2.add(compte3);

        ArrayList<CompteModel> listeComptePourBanquier3 = new ArrayList<>();
        listeComptePourBanquier3.add(compte4);
        listeComptePourBanquier3.add(compte5);


//      Création des banquiers utilisants la liste des comptes
        ActorRef acteurBanquier1 = system.actorOf(BanquierActeur.props(listeComptePourBanquier1));
        BanquierModel banquier1 = new BanquierModel(1, "Christophe", listeComptePourBanquier1,acteurBanquier1);

        ActorRef acteurBanquier2 = system.actorOf(BanquierActeur.props(listeComptePourBanquier2));
        BanquierModel banquier2 = new BanquierModel(2, "Remy", listeComptePourBanquier2 ,acteurBanquier2);

        ActorRef acteurBanquier3 = system.actorOf(BanquierActeur.props(listeComptePourBanquier3));
        BanquierModel banquier3 = new BanquierModel(3, "Tom", listeComptePourBanquier3 ,acteurBanquier3);

//      Ajout des banquiers dans une liste (utile pour la banque)
        ArrayList<BanquierModel> listeBanquier = new ArrayList<>();
        listeBanquier.add(banquier1);
        listeBanquier.add(banquier2);
        listeBanquier.add(banquier3);

//      Création de la banque
        ActorRef actorBanque = system.actorOf(BanqueActeur.props(listeBanquier));
        BanqueModel banque = new BanqueModel(1, actorBanque);

//      Création des clients avec leur compte correspondant
        ActorRef actorClient1 = system.actorOf(ClientActeur.props(banque));
        ActorRef actorClient2 = system.actorOf(ClientActeur.props(banque));
        ActorRef actorClient3 = system.actorOf(ClientActeur.props(banque));




        ClientModel client1 = new ClientModel(1, actorClient1);
        ClientModel client2 = new ClientModel(2, actorClient2);
        ClientModel client3 = new ClientModel(3, actorClient3);

        //Création d'une liste de client, pratique pour faire une boucle de demande
        ArrayList<ClientModel> listeClient = new ArrayList<>();
        listeClient.add(client1);
        listeClient.add(client2);
        listeClient.add(client3);

        System.out.println("------------------------------------");
        System.out.println("Taille de la liste de client : " + listeClient.size());
        System.out.println("Taille de la liste des banquiers : " + listeBanquier.size());
        System.out.println("------------------------------------");


//      ICI SONT LANCÉ LES DEMANDES DES CLIENTS ALÉATOIREMENT
        //client1.lancement(client1.getIdClient(), randomDemande(), randomNumber(0,500), 1);
        //client2.lancement(client2.getIdClient(), randomDemande(), randomNumber(0,500), 2);
        //client2.lancement(client1.getIdClient(), randomDemande(), randomNumber(0,500), 3);
        //client3.lancement(client3.getIdClient(), randomDemande(), randomNumber(0,500), 4);
        //client3.lancement(client3.getIdClient(), randomDemande(), randomNumber(0,500), 9);



        system.terminate();
    }
}