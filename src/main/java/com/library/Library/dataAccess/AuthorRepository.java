package com.library.Library.dataAccess;

import com.library.Library.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsByNameIgnoreCase(String name);



}
