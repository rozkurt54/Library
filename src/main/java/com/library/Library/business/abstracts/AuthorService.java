package com.library.Library.business.abstracts;

import com.library.Library.dtos.author.request.AuthorRequest;
import com.library.Library.dtos.author.response.AuthorListResponse;
import com.library.Library.dtos.author.response.AuthorResponse;
import com.library.Library.dtos.book.response.BookListResponse;
import com.library.Library.entities.Author;

import java.util.List;

public interface AuthorService {
    List<AuthorListResponse> getAll();
    AuthorResponse getById(Long id);
    AuthorResponse add(AuthorRequest authorRequest) throws Exception;
    AuthorResponse update(Long id, AuthorRequest authorRequest) throws Exception;
    void  delete(Long id);
    AuthorResponse toAuthorResponse(Author author);
    AuthorListResponse toAuthorListResponse(Author author);
    Author getAuthorById(Long id);
    List<BookListResponse> getAuthorBookList(Long id);
}
