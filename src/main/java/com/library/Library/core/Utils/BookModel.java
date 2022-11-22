package com.library.Library.core.Utils;


import com.library.Library.dtos.book.response.BookListResponse;
import com.library.Library.dtos.book.response.BookResponse;
import com.library.Library.entities.Book;


public class BookModel {

    public static BookListResponse toBookListResponse(Book book) {

        BookListResponse bookListResponse = new BookListResponse();

        bookListResponse.setId(book.getId());
        bookListResponse.setName(book.getName());
        bookListResponse.setPageCount(book.getPageCount());

        return bookListResponse;

    }

    public static BookResponse toBookResponse(Book book) {

        BookResponse bookResponse = new BookResponse();

        bookResponse.setId(book.getId());
        bookResponse.setName(book.getName());
        bookResponse.setPageCount(book.getPageCount());

        if(book.getAuthor() != null) {
            bookResponse.setAuthorId(book.getAuthor().getId());
        }

        return bookResponse;

    }


}


