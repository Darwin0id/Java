/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author darwin
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"Title", "Descirption", "Duration", "picturePath", "Genre", "Actors", "Director"})
public class Movie {
    
    @XmlAttribute
    private int id;
    private String Title;
    private String Descirption;
    private String picturePath;
    private int Duration;
    @XmlElementWrapper
    @XmlElement(name = "Genre")
    private List<Genre> Genre;
    @XmlElementWrapper
    @XmlElement(name = "Director")
    private List<Actor> Director;
    @XmlElementWrapper
    @XmlElement(name = "Actors")
    private List<Actor> Actors;
    
     public Movie() {
        Genre = new ArrayList<>();
        Actors = new ArrayList<>();
        Director = new ArrayList<>();
    }
    
    public Movie(int id, String Title, String Descirption, String picturePath, int Duration) {
        this(Title, Descirption, picturePath, Duration);
        this.id = id;
        Genre = new ArrayList<>();
        Actors = new ArrayList<>();
        Director = new ArrayList<>();
    }

    public Movie(String Title, String Descirption, String picturePath, int Duration) {
        this.Title = Title;
        this.Descirption = Descirption;
        this.picturePath = picturePath;
        this.Duration = Duration;
    }
    
    public Movie(int id, String Title, String Descirption, String picturePath, int Duration, List<Genre> Genre, List<Actor> Director, List<Actor> Actors) {
        this(id, Title, Descirption, picturePath, Duration);
        this.Genre = Genre;
        this.Director = Director;
        this.Actors = Actors;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescirption() {
        return Descirption;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public int getDuration() {
        return Duration;
    }

    public List<Genre> getGenre() {
        return Genre;
    }

    public List<Actor> getDirector() {
        return Director;
    }

    public List<Actor> getActors() {
        return Actors;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setDescirption(String Descirption) {
        this.Descirption = Descirption;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setDuration(int Duration) {
        this.Duration = Duration;
    }

    public void setGenre(List<Genre> Genre) {
        this.Genre = Genre;
    }

    public void setDirector(List<Actor> Director) {
        this.Director = Director;
    }

    public void setActros(List<Actor> Actors) {
        this.Actors = Actors;
    }
    
    @Override
    public String toString() {
        return "Movie: " + Title;
    }
}
