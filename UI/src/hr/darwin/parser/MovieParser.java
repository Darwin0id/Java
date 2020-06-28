/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hr.darwin.parser;

import hr.darwin.dal.RepositoryFactory;
import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.utils.FileUtils;
import hr.darwin.model.Actor;
import hr.darwin.model.Director;
import hr.darwin.model.Genre;
import hr.darwin.model.Movie;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import hr.darwin.handler.movie.IMovie;

/**
 *
 * @author darwin
 */
public class MovieParser {
    private static final String RSS_URL = "https://www.blitz-cinestar.hr/rss.aspx?najava=1";
    private static final int TIMEOUT = 10000;
    private static final String REQUEST_METHOD = "GET";
    private static final String FOLDER_PATH = "src\\assets\\movie";
    private static final String DELIMITER = "\\,";
    private static final String DELIMITER_SPACE = " ";
    private static final String STRREGEX = "<[^>]*>";
    private static final String[] MOVIE_TYPE = {"3D", "4DX", "IMAX", "DI"};
    
    private static final IMovie movieHandler = RepositoryFactory.getMovieHandler();
    
    public static List<Movie> parse() throws Exception {
        Set<String> existsMoviesTitle = movieHandler.selectMoviesTitle();
        List<Movie> movies = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL, TIMEOUT, REQUEST_METHOD);
        XMLEventReader reader = ParserFactory.createStaxParser(con.getInputStream());
        Optional<TagType> tagType = Optional.empty();
        
        Movie movie = null;
        StartElement startElement = null;
        List<Genre> genres = null;
        List<Actor> actros = null;
        List<Director> directors = null;
        Boolean movieTypeExists = true;
        Boolean movieExists = true;
        
        while (reader.hasNext()) {            
            XMLEvent event = reader.nextEvent();
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    tagType = TagType.from(qName);
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if(tagType.isPresent()) {
                        Characters characters = event.asCharacters();
                        String data = characters.getData().trim(); 
                        switch (tagType.get()) {
                            case ITEM:
                                if (movieTypeExists && movieExists) {
                                    genres = new ArrayList<>();
                                    actros = new ArrayList<>();
                                    directors = new ArrayList<>();
                                    movie = new Movie();
                                    movie.setGenre(genres);
                                    movie.setActros((List<Actor>)(List<?>)actros);
                                    movie.setDirector((List<Actor>)(List<?>)directors);
                                    movies.add(movie);   
                                    break;  
                                }
                                movieTypeExists = true;
                                movieExists = true;
                            case TITLE:
                                if (movie != null && !data.isEmpty()) {
                                    if (!Arrays.stream(MOVIE_TYPE).parallel().anyMatch(data::contains) && !existsMoviesTitle.contains(data)) {
                                        movie.setTitle(data);
                                        break;
                                    }
                                    movieTypeExists = false;
                                    movieExists = false;
                                }
                                break;
                            case DESCRIPTION:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setDescirption(data.replaceAll(STRREGEX, ""));
                                }
                                break;
                            case DURATION:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setDuration(Integer.parseInt(data));
                                }
                                break;
                            case GENRE:
                                if (movie != null && !data.isEmpty()) {
                                    String[] rssGenres = data.split(DELIMITER);
                                    for (String genre : rssGenres) {
                                        genres.add(new Genre(genre.toLowerCase().trim()));
                                    }
                                }
                                break;
                            case DIRECTOR:
                                if (movie != null && !data.isEmpty()) {
                                    String[] rssDirector = data.split(DELIMITER);
                                    for (String director : rssDirector) {
                                        String[] directorStrings = director.trim().split(DELIMITER_SPACE);
                                        if (directorStrings.length > 1) {
                                            directors.add(new Director(directorStrings[0], directorStrings[1]));                                            
                                        } else {
                                            directors.add(new Director());
                                        }

                                    }
                                }
                                break;
                            case ACTROS:
                                if (movie != null && !data.isEmpty()) {
                                    String[] rssActros = data.split(DELIMITER);
                                    for (String actro : rssActros) {
                                        String[] actroStrings = actro.trim().split(DELIMITER_SPACE);
                                        if (actroStrings.length >= 2) {
                                            actros.add(new Actor(actroStrings[0], actroStrings[1]));   
                                        } else if(actroStrings.length == 1){
                                            actros.add(new Actor(actroStrings[0], ""));                                              
                                        }   else {
                                            actros.add(new Actor());
                                        }
                                    }
                                }
                                break;
                            case LINK:
                                if (movie != null && !data.isEmpty()) {
                                    if (movieTypeExists && movieExists) {
                                        movie.setPicturePath(FileUtils.handlePicture(movie, data, FOLDER_PATH));
                                    }
                                }
                                break;
                        }
                    }
                    break;
            }
        }
        
        return movies;
    }    
}
