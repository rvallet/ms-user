package com.vallet.ms_user.service;

public interface PasswordService {

    String hashPassword(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
    boolean isPasswordValid(String password, String regexPattern);

}
