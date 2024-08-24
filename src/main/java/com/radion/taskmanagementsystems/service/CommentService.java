package com.radion.taskmanagementsystems.service;

import com.radion.taskmanagementsystems.dto.CommentCreateDto;
import com.radion.taskmanagementsystems.entity.Comment;
import com.radion.taskmanagementsystems.mapper.impl.MapperCommentImpl;
import com.radion.taskmanagementsystems.repository.CommentJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {
    private CommentJpaRepository commentJpaRepository;
    private MapperCommentImpl mapperComment;

    public Comment createComment(CommentCreateDto commentCreateDto){
        return commentJpaRepository.save(mapperComment.toEntity(commentCreateDto));
    }
}
