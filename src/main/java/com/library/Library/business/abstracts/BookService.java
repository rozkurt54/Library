package com.library.Library.business.abstracts;

import com.library.Library.dtos.book.request.BookRequest;
import com.library.Library.dtos.book.response.BookListResponse;
import com.library.Library.dtos.book.response.BookResponse;
import com.library.Library.dtos.image.ImageListResponse;
import com.library.Library.entities.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    List<BookListResponse> getAll();
    BookResponse getById(Long id);
    BookResponse add(BookRequest bookRequest) throws Exception;
    BookResponse update(BookRequest bookRequest, Long id) throws Exception;
    void delete(Long id);
    List<ImageListResponse> getBookImages(Long bookId) throws Exception;

    ImageListResponse addBookImage(Long id, MultipartFile multipartFile) throws Exception;
}
