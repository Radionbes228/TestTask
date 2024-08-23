package com.radion.taskmanagementsystems.mapper.impl;

import com.radion.taskmanagementsystems.dto.CommentCreateDto;
import com.radion.taskmanagementsystems.entity.Comment;
import com.radion.taskmanagementsystems.mapper.MapperComment;
import com.radion.taskmanagementsystems.service.TaskService;
import com.radion.taskmanagementsystems.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MapperCommentImpl implements MapperComment {
    private TaskService taskService;
    private UserService userService;

    @Override
    public Comment toEntity(CommentCreateDto commentCreateDto) {
        return Comment.builder()
                .text(commentCreateDto.getText())
                .task(taskService.getTaskById(commentCreateDto.getTaskId()))
                .author(userService.findById(commentCreateDto.getAuthorId()))
                .build();
    }
}
