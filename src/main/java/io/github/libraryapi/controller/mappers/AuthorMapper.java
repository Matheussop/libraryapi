package io.github.libraryapi.controller.mappers;

import io.github.libraryapi.controller.dto.AuthorDTO;
import io.github.libraryapi.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorDTO authorDTO);

    AuthorDTO toAuthorDTO(Author author);
}
