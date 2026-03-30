package io.github.libraryapi.controller.mappers;

import io.github.libraryapi.controller.dto.CreateBookDTO;
import io.github.libraryapi.controller.dto.ResultBookDTO;
import io.github.libraryapi.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntityCreate(CreateBookDTO BookDTO);
    CreateBookDTO toCreateDTO(Book book);

    Book toEntityResult(ResultBookDTO BookDTO);
    ResultBookDTO toResultDTO(Book book);
}
