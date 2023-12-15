package com.rabbitmqtest.spingAMPQ.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Body implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;

    private String message;


    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Body{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
