package org.example.DAO;
import org.example.model.BanquierModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BanquierDAO extends DAO<BanquierModel>{
    @Override
    public BanquierModel create(BanquierModel obj) {
//        try{
//            Statement stmtExecuteUpdate = this.connect.createStatement();
//            stmtExecuteUpdate.executeUpdate("INSERT INTO banquier VALUES ()");
//            stmtExecuteUpdate.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    @Override
    public BanquierModel get(long id) {
        BanquierModel banquier = new BanquierModel();
        try{
            ResultSet result = this.connect
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE
                    )
                    .executeQuery("select idBanquier, nom from banquier where idBanquier = " + id);
            if(result.first()){
                banquier = new BanquierModel(
                        id,
                        result.getString("nom")
                );
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return banquier;
    }

    @Override
    public void delete(BanquierModel obj) {

    }

    @Override
    public BanquierModel update(BanquierModel ojb) {
        return null;
    }
}
