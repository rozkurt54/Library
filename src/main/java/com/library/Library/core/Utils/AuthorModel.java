package com.library.Library.core.Utils;

import com.fasterxml.jackson.annotation.JsonKey;
import com.library.Library.dtos.author.response.AuthorListResponse;
import com.library.Library.dtos.author.response.AuthorResponse;
import com.library.Library.entities.Author;

import java.util.Objects;

public class AuthorModel {

  public static AuthorListResponse toAuthorListResponse(Author author) {
    AuthorListResponse authorListResponse = new AuthorListResponse();
    authorListResponse.setId(author.getId());
    authorListResponse.setName(author.getName());
    authorListResponse.setImages(ImageModel.toModelList(author.getImage()));
    authorListResponse.setBookCount(author.getBookList().size());
    return authorListResponse;
  }
  public static AuthorResponse toAuthorResponse(Author author) {
    AuthorResponse authorResponse = new AuthorResponse();
    authorResponse.setId(author.getId());
    authorResponse.setName(author.getName());
    if(Objects.nonNull(author.getImage())) {
      authorResponse.setImages(ImageModel.toModelList(author.getImage()));
    }
    authorResponse.setBookCount(author.getBookList().size());


    return authorResponse;
  }

}
