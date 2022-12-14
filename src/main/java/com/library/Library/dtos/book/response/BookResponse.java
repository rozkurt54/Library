package com.library.Library.dtos.book.response;


import com.library.Library.dtos.image.ImageListResponse;
import lombok.Data;

import java.util.List;

@Data
public class BookResponse {

    private Long id;
    private String name;
    private int pageCount;
    private Long authorId;

    private List<ImageListResponse> images;
}
