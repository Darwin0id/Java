/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.handler.actor;

import hr.darwin.dal.sql.DataSourceSingleton;
import hr.darwin.model.Actor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author darwin
 */
public class ActorRepo implements IActor {
    
    private static final String ACTOR_ID = "IDGlumac";
    private static final String ACTOR_FIRSTNAME = "GlumacIme";
    private static final String ACTOR_LASTNAME = "GlumacPrezime";
    private static final String ACTOR_TYPE = "Tip";
    
    private static final String CREATE_ACTOR = "{ CALL createActor(?, ?, ?)}";
    private static final String CREATE_ACTORS = "{CALL createActors(?, ?, ?)}";
    private static final String CREATE_MOVIE_ACTOR = "{CALL createMovieActor(?, ?)}";
    
    private static final String SELECT_ACTORS_BY_MOVIE = "{CALL selectActrosByMovieID(?)}";
    
    private static final String SELECT_EMPLOYEE = "{CALL selectEmployee}";
    private static final String SELECT_PERSON = "{CALL selectPerson(?)}";
    private static final String UPDATE_PERSON = "{CALL updatePerson(?, ?, ?, ?)}";
    private static final String DELETE_PERSON = "{ CALL deletePerson(?,?)}";
    
    @Override
    public List<Actor> selectEmployee() throws Exception {
        List<Actor> persons = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_EMPLOYEE); ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                persons.add(new Actor(
                        rs.getInt(ACTOR_ID),
                        rs.getString(ACTOR_FIRSTNAME),
                        rs.getString(ACTOR_LASTNAME),
                        rs.getString(ACTOR_TYPE)
                ));
            }
        }        
        return persons;
    }
    
     @Override
    public Optional<Actor> selectPerson(int selectedPersonID) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_PERSON)) {
            stmt.setInt(1, selectedPersonID);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Actor(
                            rs.getInt(ACTOR_ID),
                            rs.getString(ACTOR_FIRSTNAME),
                            rs.getString(ACTOR_LASTNAME),
                            rs.getString(ACTOR_TYPE)
                    ));
                }
            }
        }
        return Optional.empty();
    }
    
    @Override
    public int updatePerson(Actor selectedPerson) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_PERSON)) {

            stmt.setInt(1, selectedPerson.id);
            stmt.setString(2, selectedPerson.firstName);
            stmt.setString(3, selectedPerson.lastName);
            stmt.registerOutParameter(4, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(4);
        }
    }
    
    @Override
    public int deletePerson(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_PERSON)) {

            stmt.setInt(1, id);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(2);
        }
    }
    
    @Override
    public int createActor(Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {

            stmt.setString(1, actor.getFirstName());
            stmt.setString(2, actor.getLastName());
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }
    
    @Override
    public void createActors(int movieID, List<Actor> actors) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ACTORS)) {
            for (Actor actor : actors) {
                stmt.setString(1, actor.firstName);
                stmt.setString(2, actor.lastName);
                stmt.registerOutParameter(3, Types.INTEGER);
                stmt.executeUpdate();
                
                int actorID = stmt.getInt(3);
                createMovieActors(movieID, actorID);
            }
        }
    }
    
    @Override
    public void createMovieActors(int movieID, int actorID) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE_ACTOR)) {
            stmt.setInt(1, movieID);
            stmt.setInt(2, actorID);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Actor> selectActrosByMovieID(int id) throws Exception {
        List<Actor> actros = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ACTORS_BY_MOVIE)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {                    
                    actros.add(new Actor(rs.getInt(ACTOR_ID), rs.getString(ACTOR_FIRSTNAME), rs.getString(ACTOR_LASTNAME)));
                }
            }
        }
        return actros;
    }
}
