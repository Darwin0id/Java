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
public class Genre implements Comparable<Genre>{
    
    @XmlAttribute
    private int id;
    private String name;

    public Genre(int id, String name) {
        this(name);
        this.id = id;
    }

    public Genre(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Genre o) {
        return id == o.id ? 0 : 1;
    }

}
