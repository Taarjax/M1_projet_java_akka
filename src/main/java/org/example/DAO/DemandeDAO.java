package org.example.DAO;

import org.example.actors.ClientActeur;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DemandeDAO extends DAO<ClientActeur.demandeClient> {
    @Override
    public ClientActeur.demandeClient create(ClientActeur.demandeClient obj) {
        try {


            // Utiliser SELECT LAST_INSERT_ID() pour obtenir l'ID de la dernière ligne insérée dans la table demande
            Statement statement = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery("SELECT LAST_INSERT_ID()");

            // Récupérer l'objet demandeClient en utilisant l'ID obtenu
            if (result.first()) {
                long id = result.getLong(1);
                PreparedStatement prepare = this.connect.prepareStatement("insert into demande (type,montant,idClient,idCompte) values (?,?,?,?)");

                prepare.setString(1, obj.getDemande());
                prepare.setLong(2, obj.getMontant());
                prepare.setLong(3, obj.getIdClient());
                prepare.setLong(4, obj.getIdCompte());

                // Exécuter la requête préparée pour insérer la nouvelle ligne dans la table demande
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
    public ClientActeur.demandeClient get(long id) {
        ClientActeur.demandeClient demande = new ClientActeur.demandeClient();

        try {
            Statement stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet result = stmt.executeQuery("SELECT * FROM demande where idDemande = " + id);

            if (result.first()) {
                demande = new ClientActeur.demandeClient(result.getLong("idClient"), result.getString("type"), result.getLong("montant"), result.getLong("idCompte"));
            }

            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(result);
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
}
