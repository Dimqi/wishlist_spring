package com.example.demo_pet_spring.dataTransferObjects;

import com.example.demo_pet_spring.entities.WishPriority;
import jakarta.persistence.*;

import java.util.List;

public class ApiResponseDto<T> {

    private String message;

    private int code;

    private boolean success;

    private T data;

    private List<T> listData;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getListData() {
        return listData;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
    }
}
