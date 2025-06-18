package com.vallet.ms_user.service;

public interface PasswordService {

    String hashPassword(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);

}
