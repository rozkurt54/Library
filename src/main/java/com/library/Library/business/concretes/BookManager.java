package com.library.Library.business.concretes;
import com.library.Library.business.abstracts.AuthorService;
import com.library.Library.business.abstracts.BookService;
import com.library.Library.business.abstracts.ImageService;
import com.library.Library.core.Utils.BookModel;
import com.library.Library.core.Utils.ImageModel;
import com.library.Library.dataAccess.BookRepository;
import com.library.Library.dtos.book.request.BookRequest;
import com.library.Library.dtos.book.response.BookListResponse;
import com.library.Library.dtos.book.response.BookResponse;
import com.library.Library.dtos.image.ImageListResponse;
import com.library.Library.entities.Author;
import com.library.Library.entities.Book;
import com.library.Library.entities.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookManager implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final ImageService imageService;


    public BookManager(BookRepository bookRepository, AuthorService authorService, ImageService imageService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.imageService = imageService;
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

    @Override
    public List<ImageListResponse> getBookImages(Long bookId) throws Exception {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new Exception("book not found"));
        List<Image> imageList = book.getImages();
        return ImageModel.toModelList(imageList);
    }

    @Override
    public ImageListResponse addBookImage(Long id, MultipartFile multipartFile) throws Exception {

        Book book = bookRepository.findById(id).orElseThrow(()-> new Exception("Book not found"));
        Image image = imageService.addImage(multipartFile);
        List<Image> imageList = book.getImages();
        imageList.add(image);
        book.setImages(imageList);
        bookRepository.save(book);
        return ImageModel.toModel(image);
    }

    @Override
    public void deleteBookImage(Long bookId, Long imageId) throws IOException {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new RuntimeException("Book not found"));
        List<Image> bookImageList = book.getImages();
        Image image = imageService.getImageById(imageId);
        bookImageList.remove(image);
        bookRepository.save(book);
        imageService.deleteImage(imageId);

    }
}
