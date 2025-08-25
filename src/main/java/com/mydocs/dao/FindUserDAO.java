package com.mydocs.dao;

import com.mydocs.exceptions.DataAccessException;
import com.mydocs.model.Login;

public interface FindUserDAO {
    Login findUserByUsername(String username) throws DataAccessException;
    int findLoginIdByUsername(String username) throws DataAccessException;
}
