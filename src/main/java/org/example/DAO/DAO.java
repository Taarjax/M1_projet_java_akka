package org.example.DAO;

import java.sql.Connection;
import java.util.List;


/**
 * Classe abstraite représentant un DAO générique permettant d'accéder à des données stockées dans une
 * base de données ou un autre système de stockage de données.
 *
 * Cette classe définit les méthodes de base que doivent implémenter tous les DAO de l'application.
 * Ces méthodes permettent de créer, récupérer, mettre à jour et supprimer des objets de données dans
 * le système de stockage de données.
 *
 * @author  [remy-auloy]
 * @param   <T>   le type d'objet géré par ce DAO
 */
public abstract class DAO<T> {

    /**
     * Connexion à la base de données.
     */
    public Connection connect = connexionMYSQL.getInstance();

    /**
     * Méthode permettant de créer un nouvel objet dans le système de stockage de données.
     *
     * @param   obj   l'objet à créer dans le système de stockage de données
     * @return  l'objet créé dans le système de stockage de données
     */
    public abstract T create(T obj);

    /**
     * Méthode permettant de récupérer un objet dans le système de stockage de données à partir de son
     * identifiant unique.
     *
     * @param   id    l'identifiant unique de l'objet à récupérer
     * @return  l'objet récupéré dans le système de stockage de données, ou null si aucun objet ne
     *          possède cet identifiant
     */
    public abstract T get(int id);


    /**
     * Méthode permettant de supprimer un objet du système de stockage de données.
     *
     * @param   obj   l'objet à supprimer du système de stockage de données
     */
    public abstract void delete(T obj);

    /**
     * Méthode permettant de mettre à jour un objet dans le système de stockage de données.
     *
     * @param   ojb   l'objet à mettre à jour dans le système de stockage de données
     * @return  l'objet mis à jour dans le système de stockage de données
     */
    public abstract T update(T ojb);

/**
 * Méthode permettant de récupérer tous les objets du système de stockage de données.
 *
 * @return  la liste de tous les objets du système de stockage de données
 *   */
    public abstract List<T> getAll();
}
