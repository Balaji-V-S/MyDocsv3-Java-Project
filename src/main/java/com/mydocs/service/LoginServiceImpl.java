package com.mydocs.service;

import com.mydocs.dao.*;
import com.mydocs.exceptions.*;
import com.mydocs.model.Login;

public class LoginServiceImpl implements LoginService {
    private final FindUserDAO findUserDAO = new FindUserDAOImpl();
    private final RoleDAO roleDAO = new RoleDAOImpl();
    @Override
    public Login login(String username, String password) throws UserNotFoundException, AuthenticationException, DataAccessException {
        Login user = findUserDAO.findUserByUsername(username);
        if (user == null) throw new UserNotFoundException("User '" + username + "' not found.");
        if (!user.getPassword().equals(password)) throw new AuthenticationException("Invalid password.");
        user.setRoles(roleDAO.findRolesByLoginId(user.getLoginId()));
        return user;
    }
}