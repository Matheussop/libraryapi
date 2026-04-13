package io.github.libraryapi.controller.mappers;

import io.github.libraryapi.controller.dto.CreateBookDTO;
import io.github.libraryapi.controller.dto.ResultBookDTO;
import io.github.libraryapi.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public abstract class BookMapper {

//    @Mapping(target = "author", expression = "java( authorRepository.findById(bookDTO.authorId()).orElse(null))")
    public abstract Book toEntity(CreateBookDTO bookDTO);

    public abstract ResultBookDTO toResultDTO(Book book);
}
