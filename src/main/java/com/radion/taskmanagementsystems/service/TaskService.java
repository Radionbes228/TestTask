package com.radion.taskmanagementsystems.service;

import com.radion.taskmanagementsystems.dto.TaskCreateDto;
import com.radion.taskmanagementsystems.dto.TaskUpdateDto;
import com.radion.taskmanagementsystems.entity.Task;
import com.radion.taskmanagementsystems.entity.User;
import com.radion.taskmanagementsystems.exception.NotFoundTaskException;
import com.radion.taskmanagementsystems.exception.UserNotFoundException;
import com.radion.taskmanagementsystems.mapper.impl.MapperTaskImpl;
import com.radion.taskmanagementsystems.repository.TaskJpaRepository;
import com.radion.taskmanagementsystems.repository.UserJpaRepository;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor

public class TaskService {

    private TaskJpaRepository taskRepository;
    private UserJpaRepository userJpaRepository;
    private MapperTaskImpl mapperTaskImpl;
    private MessageSource messageSource;
    public Task createTask(TaskCreateDto taskCreateDto) {
        return taskRepository.save(mapperTaskImpl.toEntityIsCreate(taskCreateDto));
    }

    public Task updateTask(TaskUpdateDto taskUpdateDto) {
        if (!taskRepository.existsById(taskUpdateDto.getId())){
            throw new NotFoundTaskException(messageSource.getMessage("NotFoundTaskException", null, Locale.getDefault()));
        }
        return taskRepository.save(mapperTaskImpl.toEntityIsUpdate(taskUpdateDto));
    }
    public Task getTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundTaskException(messageSource.getMessage("NotFoundTaskException", null, Locale.getDefault())));
    }
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Page<Task> getTasksByAssignee(User assignee, Pageable pageable){
        if (!userJpaRepository.existsById(assignee.getId())){
            throw new UserNotFoundException(messageSource.getMessage("UserNotFoundException", null, Locale.getDefault()));
        }
        return taskRepository.findAllByAssignee(assignee, pageable);
    }

    public Page<Task> getTasksByAuthor(User author, Pageable pageable){
        if (!userJpaRepository.existsById(author.getId())){
            throw new UserNotFoundException(messageSource.getMessage("UserNotFoundException", null, Locale.getDefault()));
        }
        return taskRepository.findAllByAuthor(author, pageable);
    }
}

