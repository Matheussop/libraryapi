package io.github.libraryapi.controller.mappers;

import io.github.libraryapi.controller.dto.CreateBookDTO;
import io.github.libraryapi.controller.dto.ResultBookDTO;
import io.github.libraryapi.model.Book;
import io.github.libraryapi.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public abstract class BookMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java( authorRepository.findById(bookDTO.authorId()).orElse(null))")
    public abstract Book toEntityCreate(CreateBookDTO bookDTO);

    public abstract ResultBookDTO toResultDTO(Book book);
}
