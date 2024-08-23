package com.radion.taskmanagementsystems.repository;

import com.radion.taskmanagementsystems.entity.Task;
import com.radion.taskmanagementsystems.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskJpaRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByAuthor(User author, Pageable pageable);
    Page<Task> findAllByAssignee(User assignee, Pageable pageable);
}
