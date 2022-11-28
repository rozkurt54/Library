package com.library.Library.dtos.image;

import lombok.Data;

@Data
public class ImageListResponse {

    private Long id;
    private String fileName;
    private String path;
    private Long size;

}
