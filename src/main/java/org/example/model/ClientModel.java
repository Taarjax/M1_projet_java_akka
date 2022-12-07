package org.example.model;

import akka.actor.ActorRef;
import org.example.actors.ClientActeur;

public class ClientModel {
// Déclaration attribut

    private long idClient;
    private long idCompte;
    private ActorRef refActeurClient;

    public ClientModel(long idClient, long idCompte, ActorRef refActeurClient) {
        this.idClient = idClient;
        this.idCompte = idCompte;
        this.refActeurClient = refActeurClient;
    }

    public void lancement(long idClient, String demande, long montant) {
        refActeurClient.tell(new ClientActeur.demandeClient(idClient, demande, montant), ActorRef.noSender());
    }


    //    GETTER ET SETTER
    public ActorRef getrefActeurClient() {
        return refActeurClient;
    }

    public void setrefActeurClient(ActorRef refActeurClient) {
        this.refActeurClient = refActeurClient;
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    public long getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(long idCompte) {
        this.idCompte = idCompte;
    }
}
