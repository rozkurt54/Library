package com.library.Library.business.abstracts;

import com.library.Library.dtos.book.request.BookRequest;
import com.library.Library.dtos.book.response.BookListResponse;
import com.library.Library.dtos.book.response.BookResponse;
import com.library.Library.entities.Book;

import java.util.List;

public interface BookService {

    List<BookListResponse> getAll();
    BookResponse getById(Long id);
    BookResponse add(BookRequest bookRequest);
    BookResponse update(Book book, Long id);
    void delete(Long id);



}
