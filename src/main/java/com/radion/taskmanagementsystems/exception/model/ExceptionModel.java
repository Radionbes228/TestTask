package com.radion.taskmanagementsystems.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ExceptionModel {
    private String status;
    private Object content;
}
