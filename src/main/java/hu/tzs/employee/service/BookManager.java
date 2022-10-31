package hu.tzs.employee.service;

import hu.tzs.employee.service.exceptions.BookAlreadyExistsException;
import hu.tzs.employee.service.exceptions.BookNotFoundException;
import hu.tzs.employee.model.Book;

import java.util.Collection;

public interface BookManager {

    Book record(Book book) throws BookAlreadyExistsException;

    Book readByIsbn(String isbn) throws BookNotFoundException;

    Collection<Book> readAll();

    Book modify(Book book);

    void delete(Book book) throws BookNotFoundException;

}
