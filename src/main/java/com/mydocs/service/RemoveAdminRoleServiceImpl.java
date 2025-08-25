package com.mydocs.service;
import com.mydocs.dao.*;
import com.mydocs.exceptions.*;

public class RemoveAdminRoleServiceImpl implements RemoveAdminRoleService {
    private final FindUserDAO findUserDAO = new FindUserDAOImpl();
    private final RoleDAO roleDAO = new RoleDAOImpl();
    private final UserRoleDAO userRoleDAO = new UserRoleDAOImpl();
    @Override
    public boolean removeAdmin(String username) throws UserNotFoundException, ConfigurationException, DataAccessException {
        int loginId = findUserDAO.findLoginIdByUsername(username);
        if (loginId == -1) throw new UserNotFoundException("User '" + username + "' not found.");
        int adminRoleId = roleDAO.findRoleIdByName("admin");
        if (adminRoleId == -1) throw new ConfigurationException("'admin' role not found in the database.");
        return userRoleDAO.removeRoleFromUser(loginId, adminRoleId);
    }
}