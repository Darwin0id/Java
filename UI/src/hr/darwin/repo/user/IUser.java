/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.repo.user;

import hr.darwin.model.User;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author darwin
 */
public interface IUser {
    Optional<User> authorization(String username, String password) throws Exception;
    int createUser(String username, String password) throws Exception;
}
