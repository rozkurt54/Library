package com.library.Library.contollers;

import com.library.Library.business.abstracts.BookService;
import com.library.Library.dtos.book.request.BookRequest;
import com.library.Library.dtos.book.response.BookListResponse;
import com.library.Library.dtos.book.response.BookResponse;
import com.library.Library.entities.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    AuthorController authorController;
    BookService bookService;

    public BookController(AuthorController bookController, BookService bookService) {
        this.authorController = bookController;
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookListResponse>> getAll() {

        List<BookListResponse> bookList = bookService.getAll();

        if(bookList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/{id}") //example.com/1
    public ResponseEntity<BookResponse> getById(@PathVariable Long id) {

        BookResponse book = bookService.getById(id);

        if(Objects.nonNull(book)) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest) throws Exception {
        BookResponse book1 = bookService.add(bookRequest);
        if (Objects.nonNull(book1)) {
            return new ResponseEntity<>(book1, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) throws Exception{
        BookResponse book1 = bookService.update(bookRequest, id);
        if (Objects.nonNull(book1)) {
            return new ResponseEntity<>(book1, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

}
