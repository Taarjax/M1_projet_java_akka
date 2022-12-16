package org.example.DAO;


import org.example.model.ClientModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO  extends DAO<ClientModel> {
    @Override
    public ClientModel create(ClientModel obj) {
        try {
            //Récupération de la prochaine valeur de l'auto-incrément de idClient
            ResultSet result = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(
                    "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'client'"
            );
            if (result.first()) {
                long id = result.getLong("AUTO_INCREMENT");
                PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO client (idClient, nom) VALUES (?,?)");
                prepare.setLong(1, id);
                prepare.setString(2, obj.getNom());
                prepare.executeUpdate();
                obj = this.get(id);
                prepare.close();
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;

    }

    @Override
    public ClientModel get(long id) {
        ClientModel client = new ClientModel();
        try {
            ResultSet result = this.connect
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE
                    )
                    .executeQuery(
                            "SELECT idClient, nom FROM client where idClient = " + id
                    );
            if (result.first()) {
                client = new ClientModel(
                        id,
                        result.getString("nom")
                );
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    @Override
    public void delete(ClientModel obj) {

    }

    @Override
    public ClientModel update(ClientModel ojb) {
        return null;
    }

    @Override
    public List<ClientModel> getAll() {
        List<ClientModel> clients = new ArrayList<>();

        try {
            ResultSet result = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(
                    "select * from Client"
            );

            while (result.next()){
                long idClient = result.getLong("idClient");
                String nom = result.getString("nom");
                ClientModel client = new ClientModel(idClient, nom);

                clients.add(client);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clients;
    }
}
