package org.example.model;

import akka.actor.ActorRef;
import org.example.actors.ClientActeur;

import java.util.ArrayList;

public class ClientModel {
// Déclaration attribut

    private final long idClient;

    private final ArrayList<CompteModel> compte;
    private final ActorRef refActeurClient;

    public ClientModel(long idClient, ArrayList<CompteModel> compte, ActorRef refActeurClient) {
        this.idClient = idClient;
        this.compte = compte;
        this.refActeurClient = refActeurClient;
    }

    public void lancement(long idClient, String demande, long montant, long idCompte) {
        refActeurClient.tell(new ClientActeur.demandeClient(idClient, demande, montant, idCompte), ActorRef.noSender());
    }


    //    GETTER ET SETTER
    public ActorRef getrefActeurClient() {
        return refActeurClient;
    }

    public long getIdClient() {
        return idClient;
    }


    public ArrayList<CompteModel> getCompte() {
        return compte;
    }
}


