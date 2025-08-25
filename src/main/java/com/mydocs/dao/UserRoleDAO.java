package com.mydocs.dao;

import com.mydocs.exceptions.DataAccessException;

public interface UserRoleDAO {
    void assignRoleToUser(int loginId, int roleId) throws DataAccessException;
    boolean removeRoleFromUser(int loginId, int roleId) throws DataAccessException;
    void deleteAllRolesByLoginId(int loginId) throws DataAccessException;
}
