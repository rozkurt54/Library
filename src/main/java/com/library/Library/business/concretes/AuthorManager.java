package com.library.Library.business.concretes;

import com.library.Library.business.abstracts.AuthorService;
import com.library.Library.core.Utils.BookModel;
import com.library.Library.dataAccess.AuthorRepository;
import com.library.Library.dtos.author.request.AuthorRequest;
import com.library.Library.dtos.author.response.AuthorListResponse;
import com.library.Library.dtos.author.response.AuthorResponse;
import com.library.Library.dtos.book.response.BookListResponse;
import com.library.Library.entities.Author;
import com.library.Library.entities.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorManager implements AuthorService {

    private final AuthorRepository authorRepository;


    public AuthorManager(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorListResponse> getAll() {

        return authorRepository.findAll().stream().map(this::toAuthorListResponse).collect(Collectors.toList());

    }

    @Override
    public AuthorResponse getById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(this::toAuthorResponse).orElse(null);
    }

    @Override
    public AuthorResponse add(AuthorRequest authorRequest) throws Exception {
        if(isExistByName(authorRequest.getName())){
            throw new Exception("Bu yazar daha önce kaydedilmiş");
        }

        Author author = new Author();
        author.setName(authorRequest.getName());
        return toAuthorResponse(authorRepository.save(author));
    }

    @Override
    public AuthorResponse update(Long id, AuthorRequest authorRequest) throws Exception {
        if(isExistByName(authorRequest.getName())) {
            throw new Exception("Bu isim daha önceden kaydedilmiş");
        }
        Optional<Author> inDbAuthor = authorRepository.findById(id);
        if(inDbAuthor.isPresent()) {
            Author author1 = inDbAuthor.get();
            author1.setName(authorRequest.getName());
            return toAuthorResponse(authorRepository.save(author1));
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    public AuthorListResponse toAuthorListResponse(Author author) {
        AuthorListResponse authorListResponse = new AuthorListResponse();
        authorListResponse.setId(author.getId());
        authorListResponse.setName(author.getName());
        return authorListResponse;
    }
    public AuthorResponse toAuthorResponse(Author author) {
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setId(author.getId());
        authorResponse.setName(author.getName());

        return authorResponse;
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public List<BookListResponse> getAuthorBookList(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        List<BookListResponse> bookListResponseList = new ArrayList<>();
        if (author.isPresent()) {
            List<Book> bookList = author.get().getBookList();
            for (Book book : bookList) {
                BookListResponse bookListResponse = BookModel.toBookListResponse(book);
                bookListResponseList.add(bookListResponse);
            }
            return bookListResponseList;
        }
       return null;
    }

    private boolean isExistByName (String authorName) {

        return authorRepository.existsByNameIgnoreCase(authorName);

    }


}
