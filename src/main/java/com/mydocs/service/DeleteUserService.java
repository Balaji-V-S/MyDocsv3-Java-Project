package com.mydocs.service;
import com.mydocs.exceptions.*;
public interface DeleteUserService {
    boolean delete(String username) throws UserNotFoundException, DataAccessException;
}