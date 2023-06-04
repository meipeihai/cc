package com.example.cc.utils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginRequest {
    @Email(message = "无效的电子邮件地址")
    @NotBlank(message = "电子邮件不能为空")
    private String email;

    @Size(min = 6, message = "密码长度必须至少为6个字符")
    @NotBlank(message = "密码不能为空")
    private String password;

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
}
