package com.library.Library.business.abstracts;

import com.library.Library.entities.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();
    Book getById(Long id);
    Book add(Book book);
    Book update(Book book, Long id);
    void delete(Long id);

}
