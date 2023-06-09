package com.example.cc.utils.request;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Date;

@Data
public class MessageRequest extends JSON {

    private String message;
    private int senderId;
    private int receiveId;
    private Date timestamp;
}
