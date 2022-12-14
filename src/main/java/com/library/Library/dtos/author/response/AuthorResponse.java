package com.library.Library.dtos.author.response;


import com.library.Library.dtos.image.ImageListResponse;
import lombok.Data;

import java.util.List;


@Data
public class AuthorResponse {
    private Long id;
    private String name;
    private List<ImageListResponse> images;

    private int bookCount;

}
