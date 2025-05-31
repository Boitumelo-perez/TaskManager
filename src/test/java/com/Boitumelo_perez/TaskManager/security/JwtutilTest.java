package com.Boitumelo_perez.TaskManager.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String SECRET = "VGhpcyBpcyBhIHRlc3Qgc2VjcmV0IGtleSEhIQ==";

    @BeforeEach
    void setup() {
        jwtUtil = new JwtUtil();
        jwtUtil.setSecret(SECRET);
    }

    @Test
    void generateAndValidateToken_Works() {
        String token = jwtUtil.generateToken("testuser");
        assertTrue(jwtUtil.validateToken(token));
        assertEquals("testuser", jwtUtil.extractUsername(token));
    }
}