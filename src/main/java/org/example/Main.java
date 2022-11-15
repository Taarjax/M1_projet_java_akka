package org.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.apache.log4j.BasicConfigurator;
import org.example.actors.Bank;
import org.example.actors.Client;

public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();


        System.out.println("Application lancé");
        ActorSystem system = ActorSystem.create("gestion-de-compte-bancaire");

        ActorRef client = system.actorOf(Client.props(1,1, 300));
        ActorRef client2 = system.actorOf(Client.props(2,2, 200));
        ActorRef client3 = system.actorOf(Client.props(3,3, 100));

        ActorRef bank = system.actorOf(Bank.props(1));

        //Le client demande a la banque de déposer 20€
        bank.tell(new Client.Deposit(20), client);
        bank.tell(new Client.Deposit(40), client2);
        bank.tell(new Client.Deposit(30), client3);

        system.terminate();
    }
}