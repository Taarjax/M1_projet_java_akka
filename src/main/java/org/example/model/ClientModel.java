package org.example.model;

import akka.actor.ActorRef;
import org.example.DAO.DAO;
import org.example.DAO.DAOFactory;
import org.example.actors.ClientActeur;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un client de la banque.
 *
 * @author [remy-auloy]
 */
public class ClientModel {
    private int idClient; // L'identifiant de ce client.
    private String Nom; // Le nom de ce client.
    private ActorRef refActeurClient; // Référence vers l'acteur représentant ce client.

    /**
     * Constructeur par défaut.
     */
    public ClientModel(){}

    /**
     * Constructeur prenant en paramètres l'identifiant et le nom du client.
     *
     * @param idClient l'identifiant du client
     * @param nom le nom du client
     */
    public ClientModel(int idClient, String nom){
        this.idClient = idClient;
        this.Nom = nom;
    }

    /**
     * Constructeur prenant en paramètres l'identifiant et l'acteur représentant le client.
     *
     * @param idClient l'identifiant du client
     * @param refActeurClient l'acteur représentant le client
     */
    public ClientModel(int idClient, ActorRef refActeurClient) {
        this.idClient = idClient;
        this.refActeurClient = refActeurClient;

    }

    /**
     * Méthode permettant de lancer une demande du client.
     *
     * @param demande la demande du client
     */
    public void lancement(ClientActeur.demandeClient demande) {
        System.out.println("Client " + idClient + " demande un : " + demande.getDemande() + " de " + demande.getMontant() + " € sur le compte : " + demande.getIdCompte());
        refActeurClient.tell(new ClientActeur.demandeClient(idClient, demande.getDemande(), demande.getMontant(), demande.getIdCompte()), ActorRef.noSender());
    }

    /**
     * Méthode permettant de récupérer la liste de tous les comptes du client.
     *
     * @return la liste de tous les comptes du client
     */
    public List<CompteModel> getComptesAllComptes() {
        DAO<CompteModel> compteDAO = DAOFactory.getCompteDAO();
        List<CompteModel> comptes = new ArrayList<>();

        // Récupérer tous les comptes enregistrés en base de données
        List<CompteModel> allComptes = compteDAO.getAll();

        // Parcourir la liste de tous les comptes et ajouter ceux qui appartiennent au client donné en paramètre à la liste à retourner
        for (CompteModel compte : allComptes) {
            if (compte.getIdClient() == this.idClient) {
                comptes.add(compte);
            }
        }
        return comptes;
    }

    /**
     * Méthode de conversion de l'objet en chaîne de caractères.
     *
     * @return la chaîne de caractères représentant l'objet
     */
    @Override
    public String toString() {
        return "Client " + this.idClient + " | nom : " + this.Nom;
    }

    /**
     * Getter permettant de récupérer l'identifiant du client.
     *
     * @return l'identifiant du client
     */
    public int getIdClient() {
        return idClient;
    }

    /**
     * Setter permettant de définir l'acteur représentant le client.
     *
     * @param refActeurClient l'acteur représentant le client
     */
    public void setRefActeurClient(ActorRef refActeurClient) {
        this.refActeurClient = refActeurClient;
    }

    /**
     * Getter permettant de récupérer le nom du client.
     *
     * @return le nom du client
     */
    public String getNom() {
        return Nom;
    }
}


