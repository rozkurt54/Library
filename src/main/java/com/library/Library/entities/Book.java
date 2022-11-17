package com.library.Library.entities;

import lombok.*;


import javax.persistence.*;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private int pageCount;

    @ManyToOne
    private Author author;


}
