package org.binaracademy.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResponseData<T> {

    private Boolean success;
    private Integer statusCode;
    private String message;
    private T data;
}
