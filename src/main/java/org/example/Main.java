package org.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.apache.log4j.BasicConfigurator;


public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();


        System.out.println("Application lancé");
        ActorSystem system = ActorSystem.create("gestion-de-compte-bancaire");

        system.terminate();
    }
}