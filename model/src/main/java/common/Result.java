package common;

import lombok.Data;

@Data
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    private Result(int code, T data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static <T> Result<T> ok(T data) {
        return new Result<>(200, data, "success");
    }

    public static <T> Result<T> failure(Integer code,String failure) {
        return new Result<>(code, null, failure);
    }
}
