package com.library.Library.dtos.author.response;

import com.library.Library.dtos.image.ImageListResponse;
import lombok.Data;

@Data
public class AuthorListResponse {

    private Long id;
    private String name;
    private ImageListResponse imageListResponse;

}
