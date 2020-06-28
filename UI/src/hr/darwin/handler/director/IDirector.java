/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.handler.director;

import hr.darwin.model.Actor;
import hr.darwin.model.Director;
import java.util.List;

/**
 *
 * @author darwin
 */
public interface IDirector {
    int createDirector(Director director) throws Exception;
    void createDirectors(int movieID, List<Actor> directors) throws Exception;
    void createMovieDirectors(int movieID, int genreID) throws Exception;
    List<Actor> selectDirectorsByMoiveID(int id) throws Exception;
}
