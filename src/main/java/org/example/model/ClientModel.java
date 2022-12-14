package org.example.model;

import akka.actor.ActorRef;
import org.example.actors.ClientActeur;

import java.util.ArrayList;

public class ClientModel {
// Déclaration attribut

    private final long idClient;

    private final ActorRef refActeurClient;

    public ClientModel(long idClient, ActorRef refActeurClient) {
        this.idClient = idClient;
        this.refActeurClient = refActeurClient;
    }

    public void lancement(long idClient, String demande, long montant, long idCompte) {
        System.out.println("Client " + idClient + " demande un : " + demande + " de " + montant + " € sur le compte : " + idCompte);


        refActeurClient.tell(new ClientActeur.demandeClient(idClient, demande, montant, idCompte), ActorRef.noSender());
    }


    //    GETTER ET SETTER
    public long getIdClient() {
        return idClient;
    }

}


