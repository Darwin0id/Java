/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.repo.user;

import hr.darwin.dal.sql.DataSourceSingleton;
import hr.darwin.model.User;
import hr.darwin.repo.user.IUser;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author darwin
 */
public class UserRepo implements IUser {
    
   private static final String USERNAME = "UserName";
    private static final String LEVEL = "UserLevel";
        
    private static final String AUTHORIZATION = "{CALL authorizationUser(?,?)}";
    private static final String CREATE_USER = "{CALL createUser(?, ?, ?)}";

    
    @Override
    public Optional<User> authorization(String username, String password) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(AUTHORIZATION)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(rs.getString(USERNAME), rs.getInt(LEVEL)));
                }
            } catch (Exception e) {
                return Optional.empty();
            }
                
        } 
        return Optional.empty();
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
