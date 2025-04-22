package com.example.maktabproject1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


public class ResponseDto<T> {

    private boolean success;
    private T data;
    private String message;

    public ResponseDto(boolean success, T data, String message){
        this.success = success;
        this.data = data;
        this.message = message;
    }

}
