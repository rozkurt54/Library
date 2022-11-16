package com.library.Library.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> bookList;



}
