package com.radion.taskmanagementsystems.repository;

import com.radion.taskmanagementsystems.entity.Comment;
import com.radion.taskmanagementsystems.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTask(Task task);
}
