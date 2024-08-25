package com.radion.taskmanagementsystems.service;

import com.radion.taskmanagementsystems.dto.CommentCreateDto;
import com.radion.taskmanagementsystems.entity.Comment;
import com.radion.taskmanagementsystems.mapper.impl.MapperCommentImpl;
import com.radion.taskmanagementsystems.repository.CommentJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentJpaRepository commentJpaRepository;

    @Mock
    private MapperCommentImpl mapperComment;

    @InjectMocks
    private CommentService commentService;

    private CommentCreateDto commentCreateDto;
    private Comment comment;

    @BeforeEach
    void setUp() {
        commentCreateDto = new CommentCreateDto();
        comment = new Comment();
    }

    @Test
    void testCreateComment_Success() {
        when(mapperComment.toEntity(commentCreateDto)).thenReturn(comment);
        when(commentJpaRepository.save(comment)).thenReturn(comment);

        Comment result = commentService.createComment(commentCreateDto);

        assertEquals(comment, result);
        verify(commentJpaRepository).save(comment);
    }
}
