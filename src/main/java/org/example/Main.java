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
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;


public class Main {

    public static String randomDemande(){
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
        CompteModel compteClient1 = new CompteModel(1, 2000);
        CompteModel compteClient2 = new CompteModel(2, 4000);

//      Ajout des comptes dans une liste (utile pour savoir quels banquiers s'occupe de quels client)
        ArrayList<CompteModel> listeComptes = new ArrayList<>();
        listeComptes.add(compteClient1);
        listeComptes.add(compteClient2);

//      Création des banquiers utilisants la liste des comptes
        ActorRef acteurBanquier = system.actorOf(BanquierActeur.props(listeComptes));
        BanquierModel banquier1 = new BanquierModel(1, acteurBanquier);

//      Ajout des banquiers dans une liste (utile pour la banque)
        ArrayList<BanquierModel> listeBanquier = new ArrayList<>();
        listeBanquier.add(banquier1);

//      Création de la banque
        ActorRef actorBanque = system.actorOf(BanqueActeur.props(listeBanquier));
        BanqueModel banque = new BanqueModel(1, actorBanque);

//      Création des clients avec leur compte correspondant
        ActorRef actorClient = system.actorOf(ClientActeur.props(banque));

//        ICI SONT LANCÉ LES DEMANDES DES CLIENTS ALÉATOIREMENT
        for (int indiceClient = 1; indiceClient <= 3; indiceClient++) {
          ClientModel client = new ClientModel(indiceClient, indiceClient,actorClient);
//          client.lancement(client.getIdClient(), randomDemande(), randomNumber(10,300));
          System.out.println(client.getIdCompte());
        }


        system.terminate();
    }
}