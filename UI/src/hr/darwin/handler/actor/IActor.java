/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.handler.actor;

import hr.darwin.model.Actor;
import java.util.List;

/**
 *
 * @author darwin
 */
public interface IActor {
    
    int createActor(Actor actor) throws Exception;
    void createActors(int movieID, List<Actor> actors) throws Exception;
    void createMovieActors(int movieID, int actorID) throws Exception;
    List<Actor> selectActrosByMovieID(int id) throws Exception;
}
