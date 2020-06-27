/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.dal;

import hr.darwin.handler.user.IUser;
import hr.darwin.handler.user.UserHandler;

/**
 *
 * @author darwin
 */
public class RepositoryFactory {

    private RepositoryFactory() {
    }
    
    public static IUser getUserHandler() {
        return new UserHandler();
    }
}

