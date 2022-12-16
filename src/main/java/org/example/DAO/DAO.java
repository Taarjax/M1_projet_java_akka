package org.example.DAO;

import java.sql.Connection;
import java.util.List;


public abstract class DAO<T> {
    public Connection connect = connexionMYSQL.getInstance();

    public abstract T create(T obj);

    public abstract T get(long id);

    public abstract void delete(T obj);

    public abstract T update(T ojb);

    public abstract List<T> getAll();
}
