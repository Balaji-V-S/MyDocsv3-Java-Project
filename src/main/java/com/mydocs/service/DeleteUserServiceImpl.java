package com.mydocs.service;

import com.mydocs.dao.DeleteUserDAO;
import com.mydocs.dao.DeleteUserDAOImpl;
import com.mydocs.dao.FindUserDAO;
import com.mydocs.dao.FindUserDAOImpl;
import com.mydocs.dao.UserRoleDAO;
import com.mydocs.dao.UserRoleDAOImpl;
import com.mydocs.exceptions.DataAccessException;
import com.mydocs.exceptions.UserNotFoundException;

public class DeleteUserServiceImpl implements DeleteUserService {

    private final FindUserDAO findUserDAO = new FindUserDAOImpl();
    private final DeleteUserDAO deleteUserDAO = new DeleteUserDAOImpl();
    // Add the UserRoleDAO to access the new method
    private final UserRoleDAO userRoleDAO = new UserRoleDAOImpl();

    @Override
    public boolean delete(String username) throws UserNotFoundException, DataAccessException {
        // Step 1: Find the user's ID
        int loginId = findUserDAO.findLoginIdByUsername(username);
        if (loginId == -1) {
            throw new UserNotFoundException("User '" + username + "' not found.");
        }

        // Step 2 (THE FIX): Delete all records from the child table (user_role) first.
        userRoleDAO.deleteAllRolesByLoginId(loginId);

        // Step 3: Now that the child records are gone, it's safe to delete the parent record (login).
        return deleteUserDAO.deleteUserById(loginId);
    }
}
