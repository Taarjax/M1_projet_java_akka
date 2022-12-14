package org.example.DAO;

import org.example.model.CompteModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompteDAO extends DAO<CompteModel> {

    @Override
    public CompteModel create(CompteModel obj) {
        try {
            PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO compte (solde, idBanquier, idclient) VALUES (?, ?, ?)");
            prepare.setLong(1, obj.getSoldeCompte());
            prepare.setLong(2, obj.getIdBanquier());
            prepare.setLong(3, obj.getIdClient());

            prepare.executeUpdate();
            prepare.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    @Override
    public CompteModel get(long id) {
        return null;
    }

    @Override
    public void delete(CompteModel obj) {

    }
}
