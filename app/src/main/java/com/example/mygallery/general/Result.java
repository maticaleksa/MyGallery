package com.example.mygallery.general;

public class Result<T> {

    private T data;
    private Exception error;

    public static <T> Result<T> success(T data) {
        Result result = new Result();
        result.data = data;
        result.error = null;
        return result;
    }

    public static <T>Result<T> error(Exception exception) {
        Result result = new Result();
        result.data = null;
        result.error = exception;
        return result;
    }

    public T getData() {
        if (data == null) {
            throw new IllegalStateException("cannot access Result data when Result is an error");
        }
        return data;
    }

    public Exception getError() {
        if (error == null) {
            throw new IllegalStateException("cannot access error when Result was a success");
        }
        return error;
    }

    public boolean success() {
        return data != null;
    }
}
