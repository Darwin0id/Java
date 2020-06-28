/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.handler.genre;

import hr.darwin.dal.sql.DataSourceSingleton;
import hr.darwin.model.Genre;
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
public class GenreRepo implements IGenre {
    
    private static final String GENRE_ID = "IDZanr";
    private static final String GENRE = "Zanr";
    
    private static final String CREATE_GENRE = "{CALL createGenre(?,?)}";
    private static final String CREATE_GENRES = "{CALL createGenres(?, ?)}";
    private static final String CREATE_MOVIE_GENRE = "{CALL createMovieGenre(?, ?)}";
    
    private static final String SELECT_GENRES_BY_MOVIE = "{CALL selectGenresByMovieID(?)}";
    private static final String SELECT_GENRES = "{CALL selectGenres}";
    private static final String SELECT_GENRE = "{CALL selectGenre(?)}";

    private static final String DELETE_GENRE = "{CALL deleteGenre(?,?)}";
    
    private static final String UPDATE_GENRE = "{CALL updateGenre(?,?,?)}";
    
    @Override
    public int createGenre(Genre genre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_GENRE)) {
            stmt.setString(1, genre.getName());
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(2);
        }
    }
    
    @Override
    public void createGenres(int movieID, List<Genre> genres) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_GENRES)) {
            for (Genre genre : genres) {
                stmt.setString(1, genre.getName());
                stmt.registerOutParameter(2, Types.INTEGER);
                stmt.executeUpdate();
                
                int genreID = stmt.getInt(2);
                createMovieGenres(movieID, genreID);
            }
        }
    }
    
    @Override
    public void createMovieGenres(int movieID, int genreID) throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE_GENRE)) {
            stmt.setInt(1, movieID);
            stmt.setInt(2, genreID);
            
            stmt.executeUpdate();
        }
    }
    
    @Override
    public List<Genre> selectGenresByMovieID(int id) throws Exception {
        List<Genre> genres = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_GENRES_BY_MOVIE)) {
            stmt.setInt(1, id);       
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {                    
                    genres.add(new Genre(rs.getInt(GENRE_ID),rs.getString(GENRE)));
                }
            }
        }
        return genres;
    }
    
    @Override
    public List<Genre> selectGenres() throws Exception {
        List<Genre> genres = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_GENRES); ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                genres.add(new Genre(
                        rs.getInt(GENRE_ID), 
                        rs.getString(GENRE)
                ));
            }
        }
        return genres;
    }
    
    @Override
    public Optional<Genre> selectGenre(int selectedGenreID) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_GENRE)) {
            stmt.setInt(1, selectedGenreID);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Genre(
                            rs.getInt(GENRE_ID),
                            rs.getString(GENRE)));
                }
            }
        }
        return Optional.empty();
    }
    
    @Override
    public int deleteGenre(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_GENRE)) {

            stmt.setInt(1, id);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(2);
        }
    }
    
    @Override
    public int updateGenre(Genre selectedGenre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_GENRE)) {

            stmt.setInt(1, selectedGenre.getId());
            stmt.setString(2, selectedGenre.getName());
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }
}
