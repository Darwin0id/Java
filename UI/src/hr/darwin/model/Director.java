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
public class Director extends Actor {
     public Director() {
        
    }
    
    public Director(int id, String firstName, String LastName) {
        this(firstName, LastName);
        this.id = id;
    }

    public Director(String firstName, String LastName) {
        this.firstName = firstName;
        this.lastName = LastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
