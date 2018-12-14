package com.example.demo.wesocket;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author kid
 */
public class Message implements Serializable {

    private  String context;

    private  String time;

    private  String url="default.jpg";

    private  Object sender;


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getSender() {
        return sender;
    }

    public void setSender(Object sender) {
        this.sender = sender;
    }
}
