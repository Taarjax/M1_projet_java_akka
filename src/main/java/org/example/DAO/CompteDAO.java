package org.example.DAO;

import org.example.model.CompteModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompteDAO extends DAO<CompteModel> {

    @Override
    public CompteModel create(CompteModel obj) {
//        try {
//
//            ResultSet result = this.connect.createStatement(
//                    ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_UPDATABLE
//            ).executeQuery(
//                    "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'compte'"
//            );
//
//            if(result.first()){
//                long id = result.getLong("AUTO_INCREMENT");
//                PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO compte (idCompte, solde, idBanquier, idClient)");
//            }
//            PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO compte (solde, idBanquier, idclient) VALUES (?, ?, ?)");
//            prepare.setLong(1, obj.getSoldeCompte());
//            prepare.setLong(2, obj.getIdBanquier());
//            prepare.setLong(3, obj.getIdClient());
//
//            prepare.executeUpdate();
//            prepare.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    @Override
    public CompteModel get(long id) {
        CompteModel compte = new CompteModel();
        try {
            ResultSet result = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(
                    "select idCompte, solde, idBanquier, idClient from compte where idCompte = " + id
            );
            if (result.first()) {
                compte = new CompteModel(
                        id,
                        result.getLong("idClient"),
                        result.getLong("idBanquier"),
                        result.getLong("solde")
                );
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return compte;
    }

    @Override
    public void delete(CompteModel obj) {

    }

    @Override
    public CompteModel update(CompteModel obj) {
        try {
            String sql = "UPDATE compte SET solde = ? WHERE idCompte = ?";
            PreparedStatement statement = this.connect.prepareStatement(sql);
            statement.setLong(1, obj.getSoldeCompte());
            statement.setLong(2, obj.getIdCompte());
            statement.executeUpdate();
            obj = this.get(obj.getIdCompte());

            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
