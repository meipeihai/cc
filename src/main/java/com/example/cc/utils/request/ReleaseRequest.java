package com.example.cc.utils.request;

import javax.validation.constraints.NotBlank;
//发布问题，发布动态，回答问题的内容
public class ReleaseRequest {

    private int messageId;
    @NotBlank
    private String message;
    @NotBlank
    private int userId;

    private int communityId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }
}
