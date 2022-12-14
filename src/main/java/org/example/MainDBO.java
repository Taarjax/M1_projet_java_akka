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

import java.util.ArrayList;
import java.util.Random;

public class MainDBO {
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

        //Creation des comptes
        DAO<CompteModel> compteDAO = DAOFactory.getCompteDAO();
        CompteModel compte1 = new CompteModel(1,1,300);

        //Liste des comptes géré par le banquier n°1
        ArrayList<CompteModel> listeComptePourBanquier1= new ArrayList<>();
        listeComptePourBanquier1.add(compte1);

        //Banquier n°1
        DAO<BanquierModel> banquier1DAO = DAOFactory.getBanquierDAO();
        ActorRef acteurBanquier1 = system.actorOf(BanquierActeur.props(listeComptePourBanquier1));
        BanquierModel banquier1 = new BanquierModel(acteurBanquier1);

        //Liste des banquiers
        ArrayList<BanquierModel> listeBanquier = new ArrayList<>();
        listeBanquier.add(banquier1);

        //Banque
        ActorRef actorBanque = system.actorOf(BanqueActeur.props(listeBanquier));
        BanqueModel banque = new BanqueModel(1, actorBanque);

        //Client n°1
        ActorRef actorClient1 = system.actorOf(ClientActeur.props(banque));
        ClientModel client1 = new ClientModel(1, actorClient1);

        client1.lancement(client1.getIdClient(), randomDemande(), randomNumber(0,500), 1);


        compteDAO.create(compte1);
        banquier1DAO.create(banquier1);



        system.terminate();

    }

}
