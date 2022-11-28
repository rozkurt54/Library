package com.library.Library.contollers;

import com.library.Library.business.abstracts.ImageService;
import com.library.Library.dtos.image.ImageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{imageFileName}")
    public ResponseEntity<byte[]> getImageFile(@PathVariable(name = "imageFileName") String fileName) throws IOException {
           ImageResponse imageResponse =  imageService.getImageFile(fileName);

           if(Objects.nonNull(imageResponse)) {
               HttpHeaders headers = new HttpHeaders();
               headers.setContentType(imageResponse.getMediaType());
               return new ResponseEntity<>(imageResponse.getBytes(), headers, HttpStatus.OK);
           }
           return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public boolean deleteImageById(@PathVariable Long id) throws Exception {
        return imageService.deleteImage(id);
    }

}
