/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.parser;

import java.util.Optional;

/**
 *
 * @author darwin
 */
public enum TagType {
    ITEM("item"),
    TITLE("title"),
    DESCRIPTION("description"),
    GENRE("zanr"),
    DURATION("trajanje"),
    DIRECTOR("redatelj"),
    ACTROS("glumci"),
    LINK("plakat");
    
    private final String name;
    
    private TagType(String name) {
        this.name = name;
    }
    
    public static Optional<TagType> from(String name) {
        for (TagType value : values()) {
            if (value.name.equals(name)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
