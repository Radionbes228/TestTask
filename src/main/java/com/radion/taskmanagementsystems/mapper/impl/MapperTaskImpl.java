package com.radion.taskmanagementsystems.mapper.impl;

import com.radion.taskmanagementsystems.dto.TaskCreateDto;
import com.radion.taskmanagementsystems.dto.TaskUpdateDto;
import com.radion.taskmanagementsystems.entity.Task;
import com.radion.taskmanagementsystems.entity.User;
import com.radion.taskmanagementsystems.mapper.MapperTask;
import com.radion.taskmanagementsystems.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MapperTaskImpl implements MapperTask {

    private UserService userService;

    @Override
    public Task toEntityIsUpdate(TaskUpdateDto dto) {
        var assignee = new User();
        if (dto.getAssigneeId() != null){
            assignee = userService.findById(dto.getAssigneeId());
        }else {
            assignee = null;
        }

        return Task.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .author(userService.findById(dto.getAuthorId()))
                .assignee(assignee)
                .build();
    }

    @Override
    public Task toEntityIsCreate(TaskCreateDto taskCreateDto) {
        var assignee = new User();
        if (taskCreateDto.getAssigneeId() != null){
            assignee = userService.findById(taskCreateDto.getAssigneeId());
        }else {
            assignee = null;
        }

        return Task.builder()
                .title(taskCreateDto.getTitle())
                .description(taskCreateDto.getDescription())
                .status(taskCreateDto.getStatus())
                .priority(taskCreateDto.getPriority())
                .author(userService.findById(taskCreateDto.getAuthorId()))
                .assignee(assignee)
                .build();
    }
}
