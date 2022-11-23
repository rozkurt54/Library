package com.library.Library.dtos.image;

import lombok.Data;
import org.springframework.http.MediaType;

@Data
public class ImageResponse {

    private byte[] bytes;
    private MediaType mediaType;

}
