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
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskJpaRepository taskRepository;
    private UserJpaRepository userJpaRepository;
    private MapperTaskImpl mapperTaskImpl;
    public void createTask(TaskCreateDto taskCreateDto) {
        taskRepository.save(mapperTaskImpl.toEntityIsCreate(taskCreateDto));
    }

    public Task updateTask(TaskUpdateDto taskUpdateDto) {
        if (!taskRepository.existsById(taskUpdateDto.getId())){
            throw new NotFoundTaskException(String.format("{NotFoundTaskException}", taskUpdateDto.getId()));
        }
        return taskRepository.save(mapperTaskImpl.toEntityIsUpdate(taskUpdateDto));
    }
    public Task getTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundTaskException(String.format("{NotFoundTaskException}", id)));
    }
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Page<Task> getTasksByAssignee(User assignee, Pageable pageable){
        if (!userJpaRepository.existsById(assignee.getId())){
            throw new UserNotFoundException(String.format("{UserNotFoundException}", assignee.getId()));
        }
        return taskRepository.findAllByAssignee(assignee, pageable);
    }

    public Page<Task> getTasksByAuthor(User author, Pageable pageable){
        if (!userJpaRepository.existsById(author.getId())){
            throw new UserNotFoundException(String.format("{UserNotFoundException}", author.getId()));
        }
        return taskRepository.findAllByAuthor(author, pageable);
    }
}

