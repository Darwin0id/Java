/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.handler.user;

/**
 *
 * @author darwin
 */
public interface IUser {
    String authorization(String username, String password) throws Exception;
    int createUser(String username, String password) throws Exception;
}
