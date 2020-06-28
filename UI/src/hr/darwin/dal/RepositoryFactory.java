/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.dal;


import hr.darwin.handler.actor.ActorRepo;
import hr.darwin.handler.actor.IActor;
import hr.darwin.handler.director.DirectorRepo;
import hr.darwin.handler.director.IDirector;
import hr.darwin.handler.genre.GenreRepo;
import hr.darwin.handler.genre.IGenre;
import hr.darwin.handler.movie.IMovie;
import hr.darwin.handler.movie.MovieRepo;
import hr.darwin.repo.user.IUser;
import hr.darwin.repo.user.UserRepo;

/**
 *
 * @author darwin
 */
public class RepositoryFactory {

    private RepositoryFactory() {
    }
    
    public static IUser getUserHandler() {
        return new UserRepo();
    }
    
    public static IMovie getMovieHandler() {
        return new MovieRepo();
    }
    
      public static IActor getActorHandler() {
        return new ActorRepo();
    }
        
    public static IDirector getDirectorHandler() {
        return new DirectorRepo();
    }
    
    public static IGenre getGenreHandler() {
        return new GenreRepo();
    }
 
}

