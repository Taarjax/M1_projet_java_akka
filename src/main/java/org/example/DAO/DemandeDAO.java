package org.example.DAO;

import org.example.actors.ClientActeur;
import javax.sql.rowset.CachedRowSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class DemandeDAO extends DAO<ClientActeur.demandeClient> {
    @Override
    public ClientActeur.demandeClient create(ClientActeur.demandeClient obj) {
        try {
            PreparedStatement prepare = this.connect.prepareStatement("insert into demande (type,montant,idClient,idCompte) values (?,?,?,?)");

            prepare.setString(1, obj.getDemande());
            prepare.setLong(2, obj.getMontant());
            prepare.setLong(3, obj.getIdClient());
            prepare.setLong(4, obj.getIdCompte());

            // Exécuter la requête préparée pour insérer la nouvelle ligne dans la table demande
            prepare.executeUpdate();

            // Récupérer l'id de la nouvelle ligne insérée
            Statement statement = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery("SELECT LAST_INSERT_ID()");

            // Récupérer l'objet demandeClient en utilisant l'ID obtenu
            if (result.first()) {
                long id = result.getLong(1);
                obj = this.get(id);
            }
            prepare.close();
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    @Override
    public ClientActeur.demandeClient get(long id) {
        ClientActeur.demandeClient demande = new ClientActeur.demandeClient();

        try {
            Statement stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet result = stmt.executeQuery("SELECT * FROM demande where idDemande = " + id);

            if (result.first()) {
                demande = new ClientActeur.demandeClient(result.getLong("idClient"), result.getString("type"), result.getLong("montant"), result.getLong("idCompte"));
            }

            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return demande;
    }

    @Override
    public void delete(ClientActeur.demandeClient obj) {

    }

    @Override
    public ClientActeur.demandeClient update(ClientActeur.demandeClient ojb) {
        return null;
    }

    @Override
    public List<ClientActeur.demandeClient> getAll() {
        return null;
    }
}
