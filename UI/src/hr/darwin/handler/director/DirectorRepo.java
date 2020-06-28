/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.handler.director;

import hr.darwin.dal.sql.DataSourceSingleton;
import hr.darwin.model.Director;
import hr.darwin.model.Actor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author darwin
 */
public class DirectorRepo implements IDirector {
    private static final String DIRECTOR_ID = "IDRedatelj";
    private static final String DIRECTOR_FIRSTNAME = "RedateljIme";
    private static final String DIRECTOR_LASTNAME = "RedateljPrezime";
    
    private static final String CREATE_DIRECTOR = "{ CALL createDirector(?, ?, ?)}";
    private static final String CREATE_DIRECTORS = "{CALL createDirectors(?, ?, ?)}";
    private static final String CREATE_MOVIE_DIRECTOR = "{CALL createMovieDirector(?, ?)}";
    
    private static final String SELECT_DIRECTORS_BY_MOVIE = "{CALL selectDirectorsByMovieID(?)}";
    
    @Override
    public int createDirector(Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {

            stmt.setString(1, director.getFirstName());
            stmt.setString(2, director.getLastName());
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }
    
    @Override
    public void createDirectors(int movieID, List<Actor> directors) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_DIRECTORS)) {
            for (Actor director : directors) {
                stmt.setString(1, director.firstName);
                stmt.setString(2, director.lastName);
                stmt.registerOutParameter(3, Types.INTEGER);
                stmt.executeUpdate();
                
                int genreID = stmt.getInt(3);
                createMovieDirectors(movieID, genreID);
            }
        }
    }
    
    @Override
    public void createMovieDirectors(int movieID, int genreID) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE_DIRECTOR)) {
            stmt.setInt(1, movieID);
            stmt.setInt(2, genreID);
            
            stmt.executeUpdate();
        }
    }
    
    @Override
    public List<Actor> selectDirectorsByMoiveID(int id) throws Exception {
        List<Actor> directors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_DIRECTORS_BY_MOVIE)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {                    
                    directors.add(new Actor(rs.getInt(DIRECTOR_ID), rs.getString(DIRECTOR_FIRSTNAME), rs.getString(DIRECTOR_LASTNAME)));
                }
            }
        }
        return directors;
    }
}
