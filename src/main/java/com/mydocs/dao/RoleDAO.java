package com.mydocs.dao;

import com.mydocs.exceptions.DataAccessException;
import com.mydocs.model.Role;
import java.util.List;

public interface RoleDAO {
    List<Role> findRolesByLoginId(int loginId) throws DataAccessException;
    int findRoleIdByName(String roleName) throws DataAccessException;
}
