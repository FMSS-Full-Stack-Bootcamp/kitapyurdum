package com.patika.kitapyurdum.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Author {

    private String name;
    private String surname;
    private String bio;
    private Set<Book> books;

}
