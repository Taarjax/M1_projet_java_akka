package org.example.DAO;
import org.example.model.BanquierModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class BanquierDAO extends DAO<BanquierModel>{
    @Override
    public BanquierModel create(BanquierModel obj) {
        try{
            Statement stmtExecuteUpdate = this.connect.createStatement();
            stmtExecuteUpdate.executeUpdate("INSERT INTO banquier VALUES ()");
            stmtExecuteUpdate.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    @Override
    public BanquierModel get(long id) {
        return null;
    }

    @Override
    public void delete(BanquierModel obj) {

    }
}
