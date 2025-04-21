package com.example.maktabproject1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> implements Serializable {

    private boolean success;
    private T data;
    private String message;

}
