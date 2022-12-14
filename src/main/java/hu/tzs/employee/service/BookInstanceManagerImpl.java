package hu.tzs.employee.service;

import hu.tzs.employee.service.exceptions.BookNotFoundException;
import hu.tzs.employee.dao.BookInstanceRepository;
import hu.tzs.employee.dao.BookRepository;
import hu.tzs.employee.dao.entity.AuthorEntity;
import hu.tzs.employee.dao.entity.BookEntity;
import hu.tzs.employee.dao.entity.BookInstanceEntity;
import hu.tzs.employee.model.Author;
import hu.tzs.employee.model.Book;
import hu.tzs.employee.model.BookInstance;
import hu.tzs.employee.model.BookState;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookInstanceManagerImpl implements BookInstanceManager {

    private final BookInstanceRepository bookInstanceRepository;

    private final BookRepository bookRepository;

    @Override
    public Collection<BookInstance> readAll() {
        return bookInstanceRepository.findAll()
            .stream()
            .map(BookInstanceManagerImpl::convertBookInstanceEntity2Model)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<BookInstance> readInstancesOfBook(Book book) {
        return bookInstanceRepository.findAllByBook(book.getIsbn())
            .stream()
            .map(BookInstanceManagerImpl::convertBookInstanceEntity2Model)
            .collect(Collectors.toList());
    }

    @Override
    public BookInstance record(Book book) throws BookNotFoundException {
        if (bookRepository.findById(book.getIsbn()).isEmpty()) {
            throw new BookNotFoundException();
        }
        BookInstance instance = new BookInstance(UUID.randomUUID().toString(), book, BookState.BORROWABLE);
        return convertBookInstanceEntity2Model(
            bookInstanceRepository.save(convertBookInstance2BookInstanceEntity(instance)));
    }

    @Override
    public BookInstance modify(BookInstance bookInstance) {
        return convertBookInstanceEntity2Model(
            bookInstanceRepository.save(convertBookInstance2BookInstanceEntity(bookInstance))
        );
    }

    @Override
    public void delete(String inventoryNo) {
        Optional<BookInstanceEntity> bookInstanceEntity = bookInstanceRepository.findById(inventoryNo);
        if (bookInstanceEntity.isEmpty()) {
            return;
        }
        bookInstanceEntity.get().setState(BookState.DISCARDED.toString());
        bookInstanceRepository.save(bookInstanceEntity.get());
    }

    @Override
    public void delete(BookInstance bookInstance) {
        bookInstance.setState(BookState.DISCARDED);
        bookInstanceRepository.save(convertBookInstance2BookInstanceEntity(bookInstance));
    }

    private static BookInstance convertBookInstanceEntity2Model(BookInstanceEntity entity) {
        BookEntity bookEntity = entity.getBook();
        return new BookInstance(
            entity.getInventoryNo(),
            new Book(bookEntity.getIsbn(),
                new Author(
                    bookEntity.getAuthor().getId(),
                    bookEntity.getAuthor().getFirstName(),
                    bookEntity.getAuthor().getLastName(),
                    bookEntity.getAuthor().getNationality()
                ),
                bookEntity.getTitle(),
                bookEntity.getLanguage()
            ),
            BookState.valueOf(entity.getState())
        );
    }

    private static BookInstanceEntity convertBookInstance2BookInstanceEntity(BookInstance instance) {
        return BookInstanceEntity.builder()
            .inventoryNo(instance.getInventoryNo())
            .book(convertBook2BookEntity(instance.getBook()))
            .state(instance.getState().toString())
            .build();
    }

    private static BookEntity convertBook2BookEntity(Book book) {
        return BookEntity.builder()
            .isbn(book.getIsbn())
            .title(book.getTitle())
            .title(book.getLanguage())
            .author(
                AuthorEntity.builder()
                    .id(book.getAuthor().getId())
                    .firstName(book.getAuthor().getFirstName())
                    .lastName(book.getAuthor().getLastName())
                    .nationality(book.getAuthor().getNationality())
                    .build()
            )
            .build();
    }
}
