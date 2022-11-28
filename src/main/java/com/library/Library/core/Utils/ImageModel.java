package com.library.Library.core.Utils;

import com.library.Library.dtos.image.ImageListResponse;
import com.library.Library.entities.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageModel {


    public static List<ImageListResponse> toModelList(List<Image> imageList) {
        List<ImageListResponse> imageListResponseList = new ArrayList<>();
        for(Image image : imageList) {
            imageListResponseList.add(toModel(image));
        }
        return imageListResponseList;
    }

    public static ImageListResponse toModel(Image image) {
        ImageListResponse imageListResponse = new ImageListResponse();
        imageListResponse.setId(image.getId());
        imageListResponse.setFileName(image.getFileName());
        imageListResponse.setSize(image.getSize());
        imageListResponse.setPath("/api/v1" + image.getPath());
        return imageListResponse;
    }

}
