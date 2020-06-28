/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.handler.movie;

import hr.darwin.dal.RepositoryFactory;
import hr.darwin.dal.sql.DataSourceSingleton;
import hr.darwin.model.Movie;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;
import hr.darwin.handler.genre.IGenre;
import hr.darwin.handler.actor.IActor;
import hr.darwin.handler.director.IDirector;
import hr.darwin.model.Actor;

/**
 *
 * @author darwin
 */
public class MovieRepo implements IMovie {
    private final IActor actorHandler;
    private final IDirector directorHandler;
    private final IGenre gendreHandler;
    
    private static final String MOVIE_ID = "ID";
    private static final String TITLE = "Naslov";
    private static final String DESCRIPTION = "Opis";
    private static final String DURATION = "Trajanje";
    private static final String IMAGE = "Slika";
    
    private static final String SELECT_MOVIE = "{CALL selectMovie(?)}";
    private static final String SELECT_MOVIES = "{CALL selectMovies}";
    private static final String SELECT_MOVIES_TITLE = "{ CALL selectMoviesTitle }";
    private static final String CREATE_MOVIE = "{CALL createMovie(?,?,?,?,?)}";
    private static final String CREATE_MOVIES = "{CALL createMovies(?,?,?,?,?)}";
    private static final String DELETE_MOVIE = "{CALL deleteMovie(?,?)}";
    private static final String DELETE_MOVIES = "{CALL deleteMovies}"; 
    private static final String UPDATE_MOVIE = "{CALL updateMovie(?,?,?,?,?)}";

    public MovieRepo() {
        this.actorHandler = RepositoryFactory.getActorHandler();
        this.directorHandler = RepositoryFactory.getDirectorHandler();
        this.gendreHandler = RepositoryFactory.getGenreHandler();
    }
    
    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIES); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {  
                movies.add(new Movie(
                        rs.getInt(MOVIE_ID),
                        rs.getString(TITLE),
                        rs.getString(DESCRIPTION),
                        rs.getString(IMAGE),
                        rs.getInt(DURATION),
                        gendreHandler.selectGenresByMovieID(rs.getInt(MOVIE_ID)),
                        directorHandler.selectDirectorsByMoiveID(rs.getInt(MOVIE_ID)),
                        actorHandler.selectActrosByMovieID(rs.getInt(MOVIE_ID))
                ));
            }
        }
        return movies;
    }
    
    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDescirption());
            stmt.setInt(3, movie.getDuration());
            stmt.setString(4, movie.getPicturePath());
            stmt.registerOutParameter(5, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(5);
        }
    }
    
    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIES)) {
            for (Movie movie : movies) {
                stmt.setString(1, movie.getTitle());
                stmt.setString(2, movie.getDescirption());
                stmt.setInt(3, movie.getDuration());
                stmt.setString(4, movie.getPicturePath());
                stmt.registerOutParameter(5, Types.INTEGER);
                stmt.executeUpdate();
                int movieID = stmt.getInt(5);
                
                if (movieID > 0) {
                    actorHandler.createActors(movieID, movie.getActors());
                    directorHandler.createDirectors(movieID, movie.getDirector());
                    gendreHandler.createGenres(movieID, movie.getGenre());
                }
            }
        } 
    }
    
    @Override
    public int deleteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {

            stmt.setInt(1, id);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(2);
        }
    }
    
    @Override
    public void deleteMovies() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIES)) {
            stmt.executeUpdate();
        }
    }
    
    @Override
    public Optional<Movie> selectMovie(int selectedMovieID) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {
            stmt.setInt(1, selectedMovieID);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Movie(
                            rs.getInt(MOVIE_ID),
                            rs.getString(TITLE),
                            rs.getString(DESCRIPTION),
                            rs.getString(IMAGE),
                            rs.getInt(DURATION),
                            gendreHandler.selectGenresByMovieID(selectedMovieID),
                            (List<Actor>)(List<?>)directorHandler.selectDirectorsByMoiveID(selectedMovieID),
                            (List<Actor>)(List<?>)actorHandler.selectActrosByMovieID(selectedMovieID)
                    ));
                }
            }
        }
        return Optional.empty();
    }
    
    @Override
    public Set<String> selectMoviesTitle() throws Exception {
        Set<String> moviesTitle = new HashSet<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES_TITLE);
                ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()) {
                moviesTitle.add(rs.getString(1));
            }
        }
        
        return moviesTitle;
    }
    
    @Override
    public void updateMovie(Movie selectedMovie) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {

            stmt.setInt(1, selectedMovie.getId());
            stmt.setString(2, selectedMovie.getTitle());
            stmt.setString(3, selectedMovie.getDescirption());
            stmt.setInt(4, selectedMovie.getDuration());
            stmt.setString(5, selectedMovie.getPicturePath());
            stmt.executeUpdate();
        }
        
        if (!selectedMovie.getActors().isEmpty()) {
            actorHandler.createActors(selectedMovie.getId(), selectedMovie.getActors());
        }
        
        if (!selectedMovie.getDirector().isEmpty()) {
            directorHandler.createDirectors(selectedMovie.getId(), selectedMovie.getDirector());
        }
        
        if (!selectedMovie.getGenre().isEmpty()) {
            gendreHandler.createGenres(selectedMovie.getId(), selectedMovie.getGenre());
        }
    }
}
