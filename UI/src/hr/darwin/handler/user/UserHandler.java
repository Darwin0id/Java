/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.handler.user;

import hr.darwin.dal.sql.DataSourceSingleton;
import hr.darwin.handler.user.IUser;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.sql.DataSource;

/**
 *
 * @author darwin
 */
public class UserHandler implements IUser {
    
    private static final String USER_LEVEL = "KorisnikRazina";
    
    private static final String CHECK_USER_EXISTS = "{CALL checkUserExists(?,?)}";
    private static final String CREATE_USER = "{CALL createUser(?, ?, ?)}";

    
    @Override
    public String authorization(String username, String password) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(CHECK_USER_EXISTS)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(USER_LEVEL);
                }
            } catch (Exception e) {
                return null;
            }
                
        } 
        return null;
    }
    
    @Override
    public int createUser(String username, String password) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
           CallableStatement stmt = con.prepareCall(CREATE_USER)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }
}
