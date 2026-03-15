package models;

import java.util.List;

public class ApiResponse<T> {

    private int statusCode;
    private T data;
    private long responseTime;

    public ApiResponse(int statusCode, T data, long responseTime) {
        this.statusCode = statusCode;
        this.data = data;
        this.responseTime = responseTime;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }
}