/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.model;

/**
 *
 * @author darwin
 */
public class User {
    private final String UserName;
    private final int UserLevel;

    public User(String UserName, int UserLevel) {
        this.UserName = UserName;
        this.UserLevel = UserLevel;
    }
    
    public boolean authentication() {
        return 1 == UserLevel;
    }
}
