package com.radion.taskmanagementsystems.mapper;

import com.radion.taskmanagementsystems.dto.CommentCreateDto;
import com.radion.taskmanagementsystems.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperComment {
    Comment toEntity(CommentCreateDto commentCreateDto);
}
