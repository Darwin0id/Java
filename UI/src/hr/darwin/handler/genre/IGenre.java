/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.handler.genre;

import hr.darwin.model.Genre;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author darwin
 */
public interface IGenre {
    
    int createGenre(Genre genre) throws Exception;
    void createGenres(int movieID, List<Genre> genres) throws Exception;
    void createMovieGenres(int movieID, int genreID) throws Exception;
    List<Genre> selectGenresByMovieID(int id) throws Exception;
    List<Genre> selectGenres() throws Exception;
    Optional<Genre> selectGenre(int selectedGenreID) throws Exception;
    int deleteGenre(int id) throws Exception;
    int updateGenre(Genre selectedGenre) throws Exception;
}
