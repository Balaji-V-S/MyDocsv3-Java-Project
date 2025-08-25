package com.mydocs.service;
import com.mydocs.exceptions.*;
import com.mydocs.model.Login;

public interface RegisterUserService {
    void register(Login login) throws InvalidInputException, UsernameAlreadyExistsException, ConfigurationException, DataAccessException;
}