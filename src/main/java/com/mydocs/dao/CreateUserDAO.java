package com.mydocs.dao;

import com.mydocs.exceptions.DataAccessException;
import com.mydocs.model.Login;

public interface CreateUserDAO {
    int createUser(Login login) throws DataAccessException;
}
