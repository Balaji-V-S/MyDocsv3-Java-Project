package com.mydocs.dao;

import com.mydocs.exceptions.DataAccessException;

public interface DeleteUserDAO {
    boolean deleteUserById(int loginId) throws DataAccessException;
}
