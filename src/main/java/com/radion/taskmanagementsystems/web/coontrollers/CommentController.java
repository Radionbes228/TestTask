package com.radion.taskmanagementsystems.web.coontrollers;

import com.radion.taskmanagementsystems.dto.CommentCreateDto;
import com.radion.taskmanagementsystems.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
@Tag(name = "Comment Controller", description = "The controller responsible for operations with comment")
public class CommentController {
    private CommentService commentService;

    @Operation(summary = "Creating a comment", description = "Allows you to add a comment to the selected task")
    @ApiResponses(
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(
                            schema = @Schema(
                                    example = "{\"error\": \"Invalid request data\"}"
                            )
                    ))
    )
    @PostMapping("/create")
    public ResponseEntity<Object> createComment(
            @RequestBody @Valid @Parameter(description = "Json string with comment parameters", required = true) CommentCreateDto commentCreateDto){
        commentService.createComment(commentCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
