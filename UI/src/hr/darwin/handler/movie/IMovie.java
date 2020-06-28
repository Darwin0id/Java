/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.handler.movie;

import hr.darwin.model.Movie;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author darwin
 */
public interface IMovie {
    List<Movie> selectMovies() throws Exception;
    int createMovie(Movie movie) throws Exception;
    void createMovies(List<Movie> movies) throws Exception;
    int deleteMovie(int id) throws Exception;
    void deleteMovies() throws Exception;
    Optional<Movie> selectMovie(int selectedMovieID) throws Exception;
    Set<String> selectMoviesTitle() throws Exception;
    void updateMovie(Movie selectedMovie) throws Exception;
}
