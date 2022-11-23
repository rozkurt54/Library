package com.library.Library.business.concretes;

import com.library.Library.business.abstracts.ImageService;
import com.library.Library.dataAccess.ImageRepository;
import com.library.Library.dtos.image.ImageResponse;
import com.library.Library.entities.Image;
import com.library.Library.entities.abstracts.MyEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageManager implements ImageService {

    private final ImageRepository imageRepository;

    private final String imageFilePath = "/images/";

    public ImageManager(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Image> getAllImage() {
        return imageRepository.findAll();
    }

    @Override
    public Image getImage(String name) {
        return imageRepository.findByFileName(name).orElse(null);
    }

    @Override
    public Image addImage(MultipartFile multipartFile) throws IOException {
        String appPath = System.getProperty("user.dir");
        String fileName = multipartFile.getOriginalFilename();
        String fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
        String contentType = multipartFile.getContentType();

        MediaType myMediaType = MediaType.valueOf(contentType);
        UUID uuid = UUID.randomUUID();

        Path path = Path.of(appPath + imageFilePath + uuid + "." + fileExt);

        Path createdFilePath = Files.createFile(path);

        byte[] imageData = multipartFile.getBytes();

        Files.write(createdFilePath, imageData);

        Image image = new Image();
        image.setSize(multipartFile.getSize());
        image.setPath(imageFilePath + uuid + "."+ fileExt);
        image.setFileName(uuid + "." + fileExt);
        image.setMediaType(myMediaType);

        return imageRepository.save(image);
    }

    @Override
    public Image updateImage(Long imageId, MultipartFile multipartFile) {
        return null;
    }

    @Override
    public boolean deleteImage(Long imageId) {
        return false;
    }

    @Override
    public ImageResponse getImageFile(String name) throws IOException {

        Optional<Image> image = imageRepository.findByFileName(name);
        if (image.isPresent()) {
            Path imagePath = Path.of(System.getProperty("user.dir") + image.get().getPath());
            System.out.println(imagePath.toString());
            byte[] fileBytes = Files.readAllBytes(imagePath);
            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setBytes(fileBytes);
            imageResponse.setMediaType(image.get().getMediaType());
            return imageResponse;
        }
        return null;
    }
}
