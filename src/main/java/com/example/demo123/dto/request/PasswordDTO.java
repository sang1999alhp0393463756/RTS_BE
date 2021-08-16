package com.example.demo123.dto.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordDTO {

	@NotBlank
	@Size(min = 3, max = 50)
	private String username;

	@NotBlank(message = "Phải nhập mật khẩu cũ")
	private String oldPassword;

	@NotBlank(message = "Phải nhập mật khẩu mới")
	@Size(min = 6, max = 40, message = "Mật khẩu phải dài 8-32 ký tự")
	private String newPassword;

	@NotBlank(message = "Phải nhắc lại mật khẩu mới")
	private String confirmNewPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	@Override
	public String toString() {
		return "PasswordDTO [oldPassword=" + oldPassword + ", newPassword=" + newPassword + ", confirmNewPassword=" + confirmNewPassword + "]";
	}
}
