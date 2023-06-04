package com.example.cc.utils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupRequest {

    @Email(message = "无效的电子邮件地址")
    @NotBlank(message = "电子邮件不能为空")
    private String email;

    @Size(min = 6, message = "密码长度必须至少为6个字符")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Size(min = 1,message = "至少一个字符")
    @NotBlank(message = "用户名不能为空")
    private String username;
    // 接下来可以添加其他字段

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
// 接下来可以添加其他getter和setter方法
}
