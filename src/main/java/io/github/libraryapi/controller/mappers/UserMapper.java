package io.github.libraryapi.controller.mappers;

import io.github.libraryapi.controller.dto.UserDTO;
import io.github.libraryapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);
}
