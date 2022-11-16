package com.library.Library.business.abstracts;

import com.library.Library.entities.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();
    Author getById(Long id);
    Author add(Author author);
    Author update(Long id, Author author);
    void  delete(Long id);
}
