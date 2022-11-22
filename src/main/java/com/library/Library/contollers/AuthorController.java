package com.library.Library.contollers;

import com.library.Library.business.abstracts.AuthorService;
import com.library.Library.dtos.author.request.AuthorRequest;
import com.library.Library.dtos.author.response.AuthorListResponse;
import com.library.Library.dtos.author.response.AuthorResponse;
import com.library.Library.dtos.book.response.BookListResponse;
import com.library.Library.entities.Author;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("api/v1/author")
public class AuthorController {

    private final AuthorService authorService;


    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;

    }

    @GetMapping
    public ResponseEntity<List<AuthorListResponse>> getAll () {

        List<AuthorListResponse> authorListResponses = authorService.getAll();

        if(authorListResponses.isEmpty()) {
            ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(authorListResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getById (@PathVariable Long id) {

        AuthorResponse authorResponse = authorService.getById(id);

        if(Objects.nonNull(authorResponse)) {
            return new ResponseEntity<>(authorResponse, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        authorService.delete(id);
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> add(@RequestBody AuthorRequest authorRequest) throws Exception{

        AuthorResponse authorResponse = authorService.add(authorRequest);

        if(Objects.nonNull(authorResponse)) {
            return new ResponseEntity<>(authorResponse, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> update(@PathVariable Long id, @RequestBody AuthorRequest authorRequest) throws Exception {

        AuthorResponse response = authorService.update(id, authorRequest);
        if(Objects.nonNull(response)) {
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/booklist")
    public ResponseEntity<List<BookListResponse>> getBookListByAuthorId(@PathVariable Long id) {
        List<BookListResponse> bookListResponseList = authorService.getAuthorBookList(id);

        if(Objects.nonNull(bookListResponseList)) {

            return new ResponseEntity<>(bookListResponseList, HttpStatus.OK);

        }

        return ResponseEntity.noContent().build();
    }
}

