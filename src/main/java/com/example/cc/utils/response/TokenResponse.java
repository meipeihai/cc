package com.example.cc.utils.response;


public class TokenResponse {


    private String token;
    private int userId;
    public TokenResponse(String token, int userId) {
        this.token = token;
        this.userId = userId;
    }

    // getter 和 setter 方法
     public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
