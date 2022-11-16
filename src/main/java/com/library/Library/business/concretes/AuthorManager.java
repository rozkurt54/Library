package com.library.Library.business.concretes;

import com.library.Library.business.abstracts.AuthorService;
import com.library.Library.dataAccess.AuthorRepository;
import com.library.Library.entities.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorManager implements AuthorService {

    AuthorRepository authorRepository;

    public AuthorManager(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElse(null);
    }

    @Override
    public Author add(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author update(Long id, Author author) {
        Optional<Author> inDbAuthor = authorRepository.findById(id);
        if(inDbAuthor.isPresent()) {
            Author author1 = inDbAuthor.get();
            author1.setName(author.getName());
            return authorRepository.save(author1);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
