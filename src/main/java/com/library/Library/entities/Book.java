package com.library.Library.entities;

import com.library.Library.entities.abstracts.MyEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Book  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;

    private int pageCount;

    @ManyToOne
    private Author author;

    @OneToMany
    @JoinTable(name = "book_images", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<Image> images = new ArrayList<>();



}
