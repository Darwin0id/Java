/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.dal.sql;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javax.sql.DataSource;

/**
 *
 * @author darwin
 */
public class DataSourceSingleton {
    
    private static final String SERVER_NAME = "localhost";
    private static final String DATABASE_NAME = "MoviesCMS";
    private static final String UID = "sa";
    private static final String PWD = "Matija12";
    private static final int SERVER_PORT = 32768;

    private static DataSource instance;
    
    private DataSourceSingleton() {
    }
    
    public static DataSource getInstance() {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }
    
    private static DataSource createInstance() {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName(SERVER_NAME);
        dataSource.setDatabaseName(DATABASE_NAME);
        dataSource.setUser(UID);
        dataSource.setPassword(PWD);     
        dataSource.setPortNumber(SERVER_PORT);
        return dataSource;        
    }
}
