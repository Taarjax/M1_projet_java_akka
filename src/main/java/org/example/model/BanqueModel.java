package org.example.model;
import akka.actor.ActorRef;

/**
 * Classe modèle représentant une banque.
 *  * @author [remy-auloy]
 */
public class BanqueModel {
    private int idBanque;
    private ActorRef refActeurBanque;

    /**
     * Constructeur prenant en paramètres l'identifiant et l'acteur représentant la banque.
     *
     * @param _idBanque l'identifiant de la banque
     * @param _refActeurBanque l'acteur représentant la banque
     */
    public BanqueModel(int _idBanque, ActorRef _refActeurBanque) {
        this.idBanque = _idBanque;
        this.refActeurBanque = _refActeurBanque;
    }

    /**
     * Getter permettant de récupérer l'acteur représentant la banque.
     *
     * @return l'acteur représentant la banque
     */
    public ActorRef getRefActeurBanque() {
        return refActeurBanque;
    }

}
