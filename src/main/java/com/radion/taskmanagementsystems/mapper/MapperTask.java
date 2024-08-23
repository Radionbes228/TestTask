package com.radion.taskmanagementsystems.mapper;

import com.radion.taskmanagementsystems.dto.TaskCreateDto;
import com.radion.taskmanagementsystems.dto.TaskUpdateDto;
import com.radion.taskmanagementsystems.entity.Task;

@org.mapstruct.Mapper(componentModel = "spring")
public interface MapperTask {
    Task toEntityIsUpdate(TaskUpdateDto taskUpdateDto);
    Task toEntityIsCreate(TaskCreateDto taskCreateDto);

}
