package org.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.apache.log4j.BasicConfigurator;
import org.example.actors.BankActor;
import org.example.actors.ClientActor;

public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();


        System.out.println("Application lancé");
        ActorSystem system = ActorSystem.create("gestion-de-compte-bancaire");

        ActorRef client = system.actorOf(ClientActor.props(1,1, 300));

        ActorRef bank = system.actorOf(BankActor.props(1));

        //Le client demande a la banque de déposer 20€
        bank.tell(new ClientActor.Deposit(20), client);

        system.terminate();
    }
}