package com.example.chatonline.entity.Message;

public class Message {
    private String toName;
    private String message;

    private String type;



    public Message() {
    }

    public Message(String toName, String message, String type) {
        this.toName = toName;
        this.message = message;
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    @Override
    public String toString() {
        return "Message{" +
                "toName='" + toName + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
