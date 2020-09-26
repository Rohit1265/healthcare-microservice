package com.centene.healthcare.apiresponse;

/**
 * This method is the unique API reponse
 * @param <T>
 */
public class ApiResponse<T> {

    private T type;
    private String message;
    private boolean status;

    public T getType() {
        return type;
    }

    public void setType(T type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
