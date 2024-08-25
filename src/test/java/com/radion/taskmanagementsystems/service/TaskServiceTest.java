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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskJpaRepository taskRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private MapperTaskImpl mapperTaskImpl;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private TaskService taskService;

    private TaskCreateDto taskCreateDto;
    private TaskUpdateDto taskUpdateDto;
    private Task task;
    private User author;
    private User assignee;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        taskUpdateDto = new TaskUpdateDto();
        taskUpdateDto.setId(1L);
        taskCreateDto = new TaskCreateDto();
        author = new User();
        author.setId(1L);
        assignee = new User();
        assignee.setId(1L);
        task = new Task();
        task.setId(1L);
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testCreateTask() {
        when(mapperTaskImpl.toEntityIsCreate(taskCreateDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(taskCreateDto);

        assertEquals(task, result);
    }

    @Test
    void testUpdateTask_Success() {
        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(mapperTaskImpl.toEntityIsUpdate(taskUpdateDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.updateTask(taskUpdateDto);

        assertEquals(task, result);
    }

    @Test
    void testUpdateTask_NotFound() {
        when(taskRepository.existsById(anyLong())).thenReturn(false);
        when(messageSource.getMessage("NotFoundTaskException", null, Locale.getDefault()))
                .thenReturn("Task not found for id");

        NotFoundTaskException thrown = assertThrows(NotFoundTaskException.class,
                () -> taskService.updateTask(taskUpdateDto));

        assertEquals("Task not found for id", thrown.getMessage());
    }

    @Test
    void testGetTaskById_Success() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertEquals(task, result);
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage("NotFoundTaskException", null, Locale.getDefault()))
                .thenReturn("Task not found for id");

        NotFoundTaskException thrown = assertThrows(NotFoundTaskException.class,
                () -> taskService.getTaskById(1L));

        assertEquals("Task not found for id", thrown.getMessage());
    }

    @Test
    void testDeleteTask() {
        when(taskRepository.existsById(anyLong())).thenReturn(true);
        taskService.deleteTask(1L);

        verify(taskRepository).deleteById(1L);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.existsById(anyLong())).thenReturn(false);
        when(messageSource.getMessage("NotFoundTaskException", null, Locale.getDefault())).thenReturn("Task not found");

        NotFoundTaskException thrown = assertThrows(NotFoundTaskException.class,
                () -> taskService.deleteTask(1L));
        assertEquals("Task not found", thrown.getMessage());
    }

    @Test
    void testGetTasksByAssignee_Success() {
        Page<Task> tasks = new PageImpl<>(Collections.singletonList(task));
        doReturn(true).when(userJpaRepository).existsById(assignee.getId());
        when(taskRepository.findAllByAssignee(assignee, pageable)).thenReturn(tasks);

        Page<Task> result = taskService.getTasksByAssignee(assignee, pageable);

        assertEquals(tasks, result);
    }

    @Test
    void testGetTasksByAssignee_UserNotFound() {
        when(userJpaRepository.existsById(anyLong())).thenReturn(false);
        when(messageSource.getMessage("UserNotFoundException", null, Locale.getDefault()))
                .thenReturn("The assignee or author with this id was not found");

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class,
                () -> taskService.getTasksByAssignee(assignee, pageable));

        assertEquals("The assignee or author with this id was not found", thrown.getMessage());
    }

    @Test
    void testGetTasksByAuthor() {
        Page<Task> tasks = new PageImpl<>(Collections.singletonList(task));
        when(userJpaRepository.existsById(anyLong())).thenReturn(true);
        when(taskRepository.findAllByAuthor(any(User.class), any(Pageable.class))).thenReturn(tasks);

        Page<Task> result = taskService.getTasksByAuthor(author, pageable);

        assertEquals(tasks, result);
    }

    @Test
    void testGetTasksByAuthor_UserNotFound() {
        when(userJpaRepository.existsById(anyLong())).thenReturn(false);
        when(messageSource.getMessage("UserNotFoundException", null, Locale.getDefault()))
                .thenReturn("User not found");

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class,
                () -> taskService.getTasksByAuthor(author, pageable));

        assertEquals("User not found", thrown.getMessage());
    }
}
