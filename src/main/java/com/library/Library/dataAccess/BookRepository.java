package com.library.Library.dataAccess;

import com.library.Library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByNameIgnoreCaseAndAuthor_Id(String name, Long authorId);

}
