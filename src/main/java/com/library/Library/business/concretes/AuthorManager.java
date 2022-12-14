package com.library.Library.business.concretes;

import com.library.Library.business.abstracts.AuthorService;
import com.library.Library.business.abstracts.ImageService;
import com.library.Library.core.Utils.AuthorModel;
import com.library.Library.core.Utils.BookModel;
import com.library.Library.core.Utils.ImageModel;
import com.library.Library.dataAccess.AuthorRepository;
import com.library.Library.dtos.author.request.AuthorRequest;
import com.library.Library.dtos.author.response.AuthorListResponse;
import com.library.Library.dtos.author.response.AuthorResponse;
import com.library.Library.dtos.book.response.BookListResponse;
import com.library.Library.dtos.image.ImageListResponse;
import com.library.Library.entities.Author;
import com.library.Library.entities.Book;
import com.library.Library.entities.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorManager implements AuthorService {

    private final AuthorRepository authorRepository;

    private final ImageService imageService;


    public AuthorManager(AuthorRepository authorRepository, ImageService imageService) {
        this.authorRepository = authorRepository;
        this.imageService = imageService;
    }

    @Override
    public List<AuthorListResponse> getAll() {

        return authorRepository.findAll().stream().map(AuthorModel::toAuthorListResponse).collect(Collectors.toList());

    }

    @Override
    public AuthorResponse getById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(AuthorModel::toAuthorResponse).orElse(null);
    }

    @Override
    public AuthorResponse add(AuthorRequest authorRequest) throws Exception {
        if(isExistByName(authorRequest.getName())){
            throw new Exception("Bu yazar daha önce kaydedilmiş");
        }

        Author author = new Author();
        author.setName(authorRequest.getName());
        return AuthorModel.toAuthorResponse(authorRepository.save(author));
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
            return AuthorModel.toAuthorResponse(authorRepository.save(author1));
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
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

    @Override
    public void createAuthorImage(Long authorId, MultipartFile multipartFile) throws Exception {

        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if(optionalAuthor.isPresent()) {
            Image image =   imageService.addImage(multipartFile);
            Author author = optionalAuthor.get();
            author.getImage().add(image);
            authorRepository.save(author);
            return;
        }
        throw new Exception("Author not Found");

    }

    public List<ImageListResponse> getAuthorImages(Long authorId) throws Exception {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new Exception("Author not found"));

        return ImageModel.toModelList(author.getImage());

    }

    @Override
    public void deleteAuthorImage(Long authorId, Long imageId) throws IOException {
        Author author = authorRepository.findById(authorId).orElseThrow(()-> new RuntimeException("Author not found"));
        List<Image> imageList = author.getImage();
        Image image = imageService.getImageById(imageId);
        imageList.remove(image);
        authorRepository.save(author);
        imageService.deleteImage(imageId);


    }
}
