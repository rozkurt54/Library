package com.library.Library.business.concretes;
import com.library.Library.business.abstracts.AuthorService;
import com.library.Library.business.abstracts.BookService;
import com.library.Library.core.Utils.BookModel;
import com.library.Library.dataAccess.BookRepository;
import com.library.Library.dtos.book.request.BookRequest;
import com.library.Library.dtos.book.response.BookListResponse;
import com.library.Library.dtos.book.response.BookResponse;
import com.library.Library.entities.Author;
import com.library.Library.entities.Book;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookManager implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;


    public BookManager(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Override
    public List<BookListResponse> getAll() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream().map(BookModel::toBookListResponse).collect(Collectors.toList());
    }

    @Override
    public BookResponse getById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(BookModel::toBookResponse).orElse(null);
    }

    @Override
    public BookResponse add(BookRequest bookRequest) throws Exception {

        if(isExistBookByNameAndAuthorId(bookRequest.getName(), bookRequest.getAuthorId())) {
            throw new Exception("Bu yazar için aynı isimde bir kitap daha önce kaydedilmiş");
        }

        Book book = new Book();
        book.setName(bookRequest.getName());
        book.setPageCount(bookRequest.getPageCount());
        Author author = authorService.getAuthorById(bookRequest.getAuthorId());
        if(Objects.nonNull(author)) {
            book.setAuthor(author);
        }
        return BookModel.toBookResponse(bookRepository.save(book));
    }

    @Override
    public BookResponse update(BookRequest bookRequest, Long id) throws Exception {


        if(isExistBookByNameAndAuthorId(bookRequest.getName(), bookRequest.getAuthorId())) {
            throw new Exception("Bu yazar için aynı isimde bir kitap daha önce kaydedilmiş");
        }


        Optional<Book> inDbBook = bookRepository.findById(id);

        if(inDbBook.isPresent()) {
            Book book1 = inDbBook.get();
            book1.setName(bookRequest.getName());
            book1.setPageCount(bookRequest.getPageCount());

            return BookModel.toBookResponse(bookRepository.save(book1));
        }

        return null;

    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private boolean isExistBookByNameAndAuthorId(String bookName, Long authorId) {
        return bookRepository.existsByNameIgnoreCaseAndAuthor_Id(bookName, authorId);
    }


}
