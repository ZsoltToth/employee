package hu.tzs.employee.controller.dto;

import hu.tzs.employee.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface BookMapper {

    BookDto book2bookDto(Book book);

    Book bookDto2Book(BookDto dto);
}
