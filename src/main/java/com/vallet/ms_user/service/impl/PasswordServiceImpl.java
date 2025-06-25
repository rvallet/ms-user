package com.vallet.ms_user.service.impl;

import com.vallet.ms_user.service.PasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    public PasswordServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Hashes the raw password using the configured PasswordEncoder.
     *
     * @param rawPassword the raw password to hash
     * @return the hashed password
     */
    @Override
    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Checks if the raw password matches the encoded password.
     *
     * @param rawPassword     the raw password to check
     * @param encodedPassword the encoded password to compare against
     * @return true if the passwords match, false otherwise
     */
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Validates the password against a regex pattern.
     *
     * @param password      the password to validate
     * @param regexPattern  the regex pattern to validate against
     * @return true if the password matches the pattern, false otherwise
     */
    @Override
    public boolean isPasswordValid(String password, String regexPattern) {
        return password != null && password.matches(regexPattern);
    }

}
