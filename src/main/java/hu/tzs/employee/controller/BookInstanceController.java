package hu.tzs.employee.controller;

import hu.tzs.employee.controller.dto.BookDto;
import hu.tzs.employee.controller.dto.BookInstanceDto;
import hu.tzs.employee.controller.dto.BookInstanceMapper;
import hu.tzs.employee.controller.dto.BookMapper;
import hu.tzs.employee.model.Book;
import hu.tzs.employee.model.BookInstance;
import hu.tzs.employee.model.BookState;
import hu.tzs.employee.service.BookInstanceManager;
import hu.tzs.employee.service.exceptions.BookNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Api(tags = "Book Instances")
@RestController
@RequiredArgsConstructor
@RequestMapping("/books/instances")
public class BookInstanceController {

    private final BookInstanceManager bookInstanceManager;

    private final BookInstanceMapper bookInstanceMapper;

    private final BookMapper bookMapper;

    @ApiOperation("Read All")
    @GetMapping("/")
    public Collection<BookInstanceDto> readAllBookInstances() {
        return bookInstanceManager.readAll()
            .stream()
            .map(bookInstanceMapper::bookInstance2BookInstanceDto)
            .collect(Collectors.toList());
    }

    @ApiOperation("Record")
    @PostMapping("/")
    public BookInstanceDto create(@RequestBody BookDto bookDto) {
        Book book = bookMapper.bookDto2Book(bookDto);
        log.debug(book.toString());
        try {
            BookInstance recordedBookInstance = bookInstanceManager.record(book);
            return bookInstanceMapper.bookInstance2BookInstanceDto(recordedBookInstance);
        } catch (BookNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping("/")
    public BookInstanceDto update(@RequestBody BookInstanceDto dto) {
        BookInstance bookInstance = new BookInstance(dto.getInventoryNo(), bookMapper.bookDto2Book(dto.getBook()),
            BookState.valueOf(dto.getState()));
        return bookInstanceMapper.bookInstance2BookInstanceDto(bookInstanceManager.modify(bookInstance));
    }

    @ApiOperation("Delete")
    @DeleteMapping("/{inventoryNo}")
    public void delete(@PathVariable String inventoryNo) {
        bookInstanceManager.delete(inventoryNo);
    }

}
