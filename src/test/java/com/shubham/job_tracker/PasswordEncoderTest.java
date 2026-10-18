package com.shubham.job_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.AssertionErrors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordEncoderTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void shouldEncodedAndVerifyThePassword(){
        String rawPassword = "Jobtracking@123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("encoded password :: "+encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(passwordEncoder.matches(rawPassword,encodedPassword));
        assertFalse(passwordEncoder.matches("WrongPassword",encodedPassword));
    }

}
