package com.thai27.chiatien.Ulti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel<T> {
    private String status;
    private String message;
    private T data;

    public static <T> ResponseModel<T> success(T data, String message) {
        return new ResponseModel<>("success", message, data);
    }

    public static <T> ResponseModel<T> warning(T data, String message) {
        return new ResponseModel<>("warning", message, data);
    }

    public static <T> ResponseModel<T> error(T data, String message) {
        return new ResponseModel<>("error", message, data);
    }
}
