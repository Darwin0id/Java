/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author darwin
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Actor implements Comparable<Actor>{
    
    @XmlAttribute
    public int id;
    public String firstName;
    public String lastName;
    public String type;

    public Actor() {
    }

    public Actor(int id, String firstName, String lastName, String type) {
        this(id, firstName, lastName);
        this.type = type;
    }
   
    public Actor(int id, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = id;
    }

    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
      public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String LastName) {
        this.lastName = LastName;
    }

    @Override
    public int compareTo(Actor o) {
        if (lastName.equals(o.lastName)) {
            return firstName.compareTo(o.firstName);
        }
        return lastName.compareTo(o.lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
    
    
