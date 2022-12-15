package org.example.model;

import akka.actor.ActorRef;
import org.example.DAO.DAO;
import org.example.DAO.DAOFactory;
import org.example.actors.ClientActeur;

import java.util.ArrayList;

public class ClientModel {
// Déclaration attribut

    private long idClient;
    private String Nom;
    private ActorRef refActeurClient;

    public ClientModel(){}

    public ClientModel(long idClient, String nom){
        this.idClient = idClient;
        this.Nom = nom;
    }

    public ClientModel(long idClient, ActorRef refActeurClient) {
        this.idClient = idClient;
        this.refActeurClient = refActeurClient;

    }

    public void lancement(ClientActeur.demandeClient demande) {
        System.out.println("Client " + idClient + " demande un : " + demande.getDemande() + " de " + demande.getMontant() + " € sur le compte : " + demande.getIdCompte());
        refActeurClient.tell(new ClientActeur.demandeClient(idClient, demande.getDemande(), demande.getMontant(), demande.getIdCompte()), ActorRef.noSender());
    }

    @Override
    public String toString() {
        return "Client " + this.idClient + " | nom : " + this.Nom;
    }

    //    GETTER ET SETTER
    public long getIdClient() {
        return idClient;
    }

    public void setRefActeurClient(ActorRef refActeurClient) {
        this.refActeurClient = refActeurClient;
    }


    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }
}


