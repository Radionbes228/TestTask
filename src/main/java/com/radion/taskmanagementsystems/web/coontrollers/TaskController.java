package com.radion.taskmanagementsystems.web.coontrollers;

import com.radion.taskmanagementsystems.dto.TaskCreateDto;
import com.radion.taskmanagementsystems.dto.TaskUpdateDto;
import com.radion.taskmanagementsystems.entity.Task;
import com.radion.taskmanagementsystems.entity.User;
import com.radion.taskmanagementsystems.service.TaskService;
import com.radion.taskmanagementsystems.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
@Tag(name = "Task Controller", description = "The controller responsible for operations with tasks")
public class TaskController {
    private TaskService taskService;
    private UserService userService;


    @Operation(summary = "Information about a specific task", description = "Allows you to get information about a specific task along with comments")
    @GetMapping("/infoTask")
    public ResponseEntity<Task> infoTask(@RequestParam(name = "id")
                                             @Parameter(description = "Task ID", required = true) Long id
    ){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(summary = "Create a task")
    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody @Valid
                                                 @Parameter(
                                                         description = "JSON string with task fields for creating the task itself",
                                                         required = true) TaskCreateDto taskCreateDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskCreateDto));
    }

    @Operation(summary = "Update a task", description = "Allows you to update a task")
    @PutMapping("/update")
    public ResponseEntity<Task> updateTask(@RequestBody @Valid
                                               @Parameter(
                                                       description = "JSON string containing an already existing task with modified parameters",
                                                       required = true
                                               ) TaskUpdateDto taskUpdateDto
    ){
        return ResponseEntity.ok(taskService.updateTask(taskUpdateDto));
    }

    @Operation(summary = "Delete a task", description = "Allows you to delete a task")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteTask(@RequestParam(name = "id")
                                               @Parameter(
                                                       description = "Task ID",
                                                       required = true
                                               ) Long id
    ) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Getting a list of tasks by author identification",
            description = "Allows you to get a list of tasks by author identification." +
                    "You can sort the receipt of tasks by status, customize the order of display, and also specify how many tasks to display for one page.")
    @GetMapping("/author")
    public ResponseEntity<Page<Task>> getTasksByAuthor(
            @RequestParam(name = "id") Long id,
            @RequestParam(required = false, name = "page", defaultValue = "0") @Parameter(
                    description = "Requested page number"
            ) Integer page,
            @RequestParam(required = false, name = "size", defaultValue = "10") @Parameter(
                    description = "Number of elements per page"
            ) Integer size,
            @RequestParam(required = false, name = "sort", defaultValue = "priority") @Parameter(
                    description = "Sort by priority or task status",
                    example = "priority: LOW || status: PENDING"
            ) String sortBy,
            @RequestParam(required = false, name = "order", defaultValue = "asc") @Parameter(
                    example = "desc"
            ) String order
    ) {

        User user = userService.findById(id);
        Pageable pageable = PageRequest
                .of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));

        Page<Task> resultPage = taskService.getTasksByAuthor(user, pageable);
        return ResponseEntity.ok(resultPage);
    }

    @Operation(summary = "Getting a list of tasks by assignee identification",
            description = "Allows you to get a list of tasks by assignee identification." +
                    "You can sort the receipt of tasks by status, customize the order of display, and also specify how many tasks to display for one page.")
    @GetMapping("/assignee")
    public ResponseEntity<Page<Task>> getTasksByAssignee(
            @RequestParam(name = "id") Long id,
            @RequestParam(required = false, name = "page", defaultValue = "0") @Parameter(
                    description = "Requested page number"
            ) Integer page,
            @RequestParam(required = false, name = "size", defaultValue = "10") @Parameter(
                    description = "Number of elements per page"
            ) Integer size,
            @RequestParam(required = false, name = "sort", defaultValue = "priority") @Parameter(
                    description = "Sort by priority or task status",
                    example = "priority: LOW || status: PENDING"
            ) String sortBy,
            @RequestParam(required = false, name = "order", defaultValue = "asc") @Parameter(
                    example = "desc"
            ) String order
    ) {

        User user = userService.findById(id);
        Pageable pageable = PageRequest
                .of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));

        Page<Task> resultPage = taskService.getTasksByAssignee(user, pageable);
        return ResponseEntity.ok(resultPage);
    }
}
