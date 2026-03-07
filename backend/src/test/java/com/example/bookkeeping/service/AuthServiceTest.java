package com.example.bookkeeping.service;

import com.example.bookkeeping.mapper.UserMapper;
import com.example.bookkeeping.model.User;
import com.example.bookkeeping.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_NewUser_Success() {
        when(userMapper.findByPhone("12345678901")).thenReturn(null);
        when(jwtUtil.generateToken(any(), any())).thenReturn("token");

        String token = authService.register("12345678901", "password");

        assertNotNull(token);
        verify(userMapper).insert(any(User.class));
    }

    @Test
    void register_ExistingUser_ThrowsException() {
        when(userMapper.findByPhone("12345678901")).thenReturn(new User());

        assertThrows(RuntimeException.class, () -> authService.register("12345678901", "password"));
    }

    @Test
    void sendCode_Success() {
        String code = authService.sendCode("12345678901");
        assertNotNull(code);
        assertEquals(6, code.length());
    }
}
