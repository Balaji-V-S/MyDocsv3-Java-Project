package com.mydocs.service;
import com.mydocs.exceptions.*;
import com.mydocs.model.Login;

public interface LoginService {
    Login login(String username, String password) throws UserNotFoundException, AuthenticationException, DataAccessException;
}