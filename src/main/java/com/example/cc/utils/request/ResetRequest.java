package com.example.cc.utils.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
//重置的传递内容
public class ResetRequest {
    @Email(message = "无效的电子邮件地址")
    @NotBlank(message = "电子邮件不能为空")
    private String email;

    @Size(min = 6, message = "密码长度必须至少为6个字符")
    @NotBlank(message = "密码不能为空")
    private String newPassword;

    @Size(min = 11)
    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String password) {
        this.newPassword = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
