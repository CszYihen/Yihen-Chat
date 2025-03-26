package com.yihenchat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private int code;
    private String msg;
    private Object data;

    public static Result ok(String msg, Object data) {
        return new Result(200, msg, data);
    }

    public static Result ok() {
        return Result.ok(null, null);
    }

    public static Result error(String msg) {
        return new Result(500, msg, null);
    }

    public static Result error() {
        return Result.error(null);
    }



}
