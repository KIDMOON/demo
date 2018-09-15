package com.example.demo.common;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author：Kid date:2018/3/3
 */
@XmlRootElement(name = "result")
public class Result implements Serializable {
    private int code;
    private String status;
    private String msg;
    private Object object;

    public static final int CODE_200 = 200;
    public static final int CODE_500 = 500;
    public static final int CODE_400 = 400;
    public static final int CODE_311 = 311;
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAIL = "fail";
    public static final String STATUS_ERROR = "error";


    public Result() {
    }
    public  Result(String message){
        this.msg=message;
    }
    public Result(String msg, Object object) {
        this.msg = msg;
        this.object = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        if(this.status == null){
            if(this.code>=CODE_500 && this.code<=599){
                this.status  = STATUS_FAIL;
            }else if(this.code>=CODE_400 &&  this.code<=499){
                this.status  = STATUS_ERROR;
            }else{
                this.status  = STATUS_SUCCESS;
            }
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
