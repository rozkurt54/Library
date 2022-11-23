package com.library.Library.business.abstracts;

import com.library.Library.dtos.image.ImageResponse;
import com.library.Library.entities.Image;
import com.library.Library.entities.abstracts.MyEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    List<Image> getAllImage();

    Image getImage(String name);

    Image addImage(MultipartFile multipartFile) throws IOException;

    Image updateImage(Long imageId, MultipartFile multipartFile);

    boolean deleteImage(Long imageId);

    ImageResponse getImageFile(String name) throws IOException;



}
