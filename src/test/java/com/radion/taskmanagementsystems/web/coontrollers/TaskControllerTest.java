package com.radion.taskmanagementsystems.web.coontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radion.taskmanagementsystems.dto.TaskCreateDto;
import com.radion.taskmanagementsystems.dto.TaskUpdateDto;
import com.radion.taskmanagementsystems.entity.Task;
import com.radion.taskmanagementsystems.entity.User;
import com.radion.taskmanagementsystems.entity.enums.Priority;
import com.radion.taskmanagementsystems.entity.enums.Status;
import com.radion.taskmanagementsystems.service.TaskService;
import com.radion.taskmanagementsystems.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EnableWebMvc
@AutoConfigureMockMvc
public class TaskControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;
    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskController taskController;

    private Task task;
    private TaskCreateDto taskCreateDto;
    private TaskUpdateDto taskUpdateDto;
    private User author;
    private Page<Task> taskPage;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();

        author = new User();
        author.setId(1L);

        task = Task.builder()
                .id(1L)
                .title("title")
                .description(null)
                .status(Status.PENDING)
                .priority(Priority.MEDIUM)
                .author(author)
                .build();

        taskCreateDto = TaskCreateDto.builder()
                .title("title")
                .description(null)
                .status(Status.PENDING)
                .priority(Priority.MEDIUM)
                .authorId(author.getId())
                .build();

        taskUpdateDto = TaskUpdateDto.builder()
                .id(1L)
                .title("title")
                .description(null)
                .status(Status.PENDING)
                .priority(Priority.MEDIUM)
                .authorId(author.getId())
                .build();
        taskUpdateDto.setId(1L);
        taskUpdateDto.setTitle("Updated Task");

        taskPage = new PageImpl<>(Collections.singletonList(task), PageRequest.of(0, 10), 1);
    }

    @Test
    void testInfoTask_Success() throws Exception {
        when(taskService.getTaskById(anyLong())).thenReturn(task);

        mockMvc.perform(get("/api/tasks/infoTask")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.status").value(task.getStatus().toString()))
                .andExpect(jsonPath("$.priority").value(task.getPriority().toString()))
                .andExpect(jsonPath("$.author").value(task.getAuthor()))
                .andExpect(jsonPath("$.assignee").value(task.getAssignee()))
                .andExpect(jsonPath("$.comments").value(task.getComments()));
    }


    @Test
    void testCreateTask_Success() throws Exception {
        when(taskService.createTask(taskCreateDto)).thenReturn(task);

        mockMvc.perform(
                post("/api/tasks/create")
                        .content(objectMapper.writeValueAsString(taskCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.status").value(task.getStatus().toString()))
                .andExpect(jsonPath("$.priority").value(task.getPriority().toString()))
                .andExpect(jsonPath("$.author").value(task.getAuthor()))
                .andExpect(jsonPath("$.assignee").value(task.getAssignee()))
                .andExpect(jsonPath("$.comments").value(task.getComments()));
    }

    @Test
    void testUpdateTask_Success() throws Exception {
        when(taskService.updateTask(any(TaskUpdateDto.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.status").value(task.getStatus().toString()))
                .andExpect(jsonPath("$.priority").value(task.getPriority().toString()))
                .andExpect(jsonPath("$.author").value(task.getAuthor()))
                .andExpect(jsonPath("$.assignee").value(task.getAssignee()));
    }

    @Test
    void testDeleteTask_Success() throws Exception {
        mockMvc.perform(delete("/api/tasks/delete")
                        .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTasksByAuthor_Success() throws Exception {
        when(userService.findById(anyLong())).thenReturn(author);
        when(taskService.getTasksByAuthor(any(User.class), any(Pageable.class))).thenReturn(taskPage);

        mockMvc.perform(get("/api/tasks/author")
                        .param("id", "1")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "priority")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(task.getId()))
                .andExpect(jsonPath("$.content[0].title").value(task.getTitle()))
                .andExpect(jsonPath("$.content[0].description").value(task.getDescription()))
                .andExpect(jsonPath("$.content[0].status").value(task.getStatus().toString()))
                .andExpect(jsonPath("$.content[0].priority").value(task.getPriority().toString()))
                .andExpect(jsonPath("$.content[0].author").value(task.getAuthor()))
                .andExpect(jsonPath("$.content[0].assignee").value(task.getAssignee()))
                .andExpect(jsonPath("$.content[0].comments").value(task.getComments()));
    }

    @Test
    void testGetTasksByAssignee_Success() throws Exception {
        when(userService.findById(anyLong())).thenReturn(author);
        when(taskService.getTasksByAssignee(any(User.class), any(Pageable.class))).thenReturn(taskPage);

        mockMvc.perform(get("/api/tasks/assignee")
                        .param("id", "1")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "priority")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(task.getId()))
                .andExpect(jsonPath("$.content[0].title").value(task.getTitle()))
                .andExpect(jsonPath("$.content[0].description").value(task.getDescription()))
                .andExpect(jsonPath("$.content[0].status").value(task.getStatus().toString()))
                .andExpect(jsonPath("$.content[0].priority").value(task.getPriority().toString()))
                .andExpect(jsonPath("$.content[0].author").value(task.getAuthor()))
                .andExpect(jsonPath("$.content[0].assignee").value(task.getAssignee()))
                .andExpect(jsonPath("$.content[0].comments").value(task.getComments()));
    }
}
