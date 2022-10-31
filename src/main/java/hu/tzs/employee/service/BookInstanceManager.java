package hu.tzs.employee.service;

import hu.tzs.employee.service.exceptions.BookNotFoundException;
import hu.tzs.employee.model.Book;
import hu.tzs.employee.model.BookInstance;

import java.util.Collection;

public interface BookInstanceManager {

    Collection<BookInstance> readAll();

    Collection<BookInstance> readInstancesOfBook(Book book);

    BookInstance record(Book book) throws BookNotFoundException;

    BookInstance modify(BookInstance bookInstance);

    void delete(String inventoryNo);

    void delete(BookInstance bookInstance);
}
