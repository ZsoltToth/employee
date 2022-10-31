package hu.tzs.employee.controller.dto;

import hu.tzs.employee.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto author2AuthorDto(Author author);

    Author authorDto2Author(AuthorDto dto);
}
