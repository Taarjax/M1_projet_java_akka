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

import java.util.ArrayList;
import java.util.Random;


public class Main {

    public static String randomDemande(){
        String[] s = new String[]{"retrait", "dépot"};
        Random ran = new Random();
        String s_ran = s[ran.nextInt(s.length)];
        return s_ran;
    }

    private static int randomMontant(int min, int max) {

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

        ActorRef acteurBanquier = system.actorOf(BanquierActeur.props());
        BanquierModel banquier1 = new BanquierModel(1, )

        ArrayList<BanquierModel> listeBanquier = new ArrayList<>();
        listeBanquier.add(banquier1);

        ActorRef actorBanque = system.actorOf(BanqueActeur.props());
        BanqueModel banque = new BanqueModel(1, actorBanque);

        ActorRef actorClient = system.actorOf(ClientActeur.props(banque));
        ClientModel client = new ClientModel(1, 1, actorClient);

        for (int indiceClient = 1; indiceClient <= 3; indiceClient++) {
            client.lancement(indiceClient, randomDemande(), randomMontant(10,300));
        }


        system.terminate();
    }
}