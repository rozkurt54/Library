package com.library.Library.entities;

import com.library.Library.entities.abstracts.MyEntity;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.http.MediaType;

import javax.persistence.*;

@Entity
@Data
public class Image{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;

    private String path;

    private Long size;

    private MediaType mediaType;



}
