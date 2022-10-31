package hu.tzs.employee.controller.dto;

import hu.tzs.employee.model.BookInstance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface BookInstanceMapper {

    BookInstanceDto bookInstance2BookInstanceDto(BookInstance bookInstance);

}
