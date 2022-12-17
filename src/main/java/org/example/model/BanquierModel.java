package org.example.model;
import akka.actor.ActorRef;

import java.util.ArrayList;

/**
 * Classe modèle représentant un banquier.
 *
 * @author [remy-auloy]
 */
public class BanquierModel {
    // Attribut
    private int idBanquier;

    private String nom;
    private ActorRef ReferenceActeurBanquier;

    private ArrayList<CompteModel> listeCompteParBanquier;


    /**
     * Constructeur par défaut.
     */
    public BanquierModel() {}

    /**
     * Constructeur prenant en paramètres l'identifiant et le nom du banquier.
     *
     * @param idBanquier l'identifiant du banquier
     * @param nom le nom du banquier
     */
    public BanquierModel(int idBanquier, String nom) {
        this.idBanquier = idBanquier;
        this.nom = nom;
    }

    /**
     * Constructeur prenant en paramètres l'identifiant, le nom, la liste de comptes gérés par le banquier et l'acteur représentant le banquier.
     *
     * @param _idBanquier l'identifiant du banquier
     * @param nom le nom du banquier
     * @param _listeCompteParBanquier la liste de comptes gérés par le banquier
     * @param _referenceActeurBanquier l'acteur représentant le banquier
     */
    public BanquierModel(int _idBanquier, String nom, ArrayList<CompteModel> _listeCompteParBanquier, ActorRef _referenceActeurBanquier) {
        this.idBanquier = _idBanquier;
        this.nom = nom;
        this.listeCompteParBanquier = _listeCompteParBanquier;
        this.ReferenceActeurBanquier = _referenceActeurBanquier;
    }

    /**
     * Getter permettant de récupérer l'identifiant du banquier.
     *
     * @return l'identifiant du banquier
     */
    public int getIdBanquier() {
        return idBanquier;
    }

    /**
     * Getter permettant de récupérer l'acteur représentant le banquier.
     *
     * @return l'acteur représentant le banquier
     */
    public ActorRef getReferenceActeurBanquier() {
        return ReferenceActeurBanquier;
    }

    /**
     * Getter permettant de récupérer la liste de comptes gérés par le banquier.
     *
     * @return la liste de comptes gérés par le banquier
     */
    public ArrayList<CompteModel> getListeCompteParBanquier() {
        return listeCompteParBanquier;
    }

    /**
     * Méthode de conversion de l'objet en chaîne de caractères.
     *
     * @return la chaîne de caractères représentant l'objet
     */
    @Override
    public String toString() {
        return "BANQUIER: " +this.idBanquier + " | NOM: "+this.nom + " gère " + listeCompteParBanquier.toString();
    }

    /**
     * Setter permettant de définir l'acteur représentant le banquier.
     *
     * @param ReferenceActeurBanquier l'acteur représentant le banquier
     */
    public void setReferenceActeurBanquier(ActorRef ReferenceActeurBanquier) {
        this.ReferenceActeurBanquier = ReferenceActeurBanquier;
    }

    /**
     * Setter permettant de définir la liste de comptes gérés par le banquier.
     *
     * @param listeCompteParBanquier la liste de comptes gérés par le banquier
     */
    public void setListeCompteParBanquier(ArrayList<CompteModel> listeCompteParBanquier) {
        this.listeCompteParBanquier = listeCompteParBanquier;
    }
}
