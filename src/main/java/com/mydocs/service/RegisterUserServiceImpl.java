package com.mydocs.service;
import com.mydocs.dao.*;
import com.mydocs.exceptions.*;
import com.mydocs.model.Login;

public class RegisterUserServiceImpl implements RegisterUserService {
    private final FindUserDAO findUserDAO = new FindUserDAOImpl();
    private final CreateUserDAO createUserDAO = new CreateUserDAOImpl();
    private final RoleDAO roleDAO = new RoleDAOImpl();
    private final UserRoleDAO userRoleDAO = new UserRoleDAOImpl();
    @Override
    public void register(Login login) throws InvalidInputException, UsernameAlreadyExistsException, ConfigurationException, DataAccessException {
        if (login.getUserName() == null || login.getUserName().trim().isEmpty()) throw new InvalidInputException("Username cannot be empty.");
        if (login.getPassword() == null || login.getPassword().isEmpty()) throw new InvalidInputException("Password cannot be empty.");
        if (findUserDAO.findUserByUsername(login.getUserName()) != null) throw new UsernameAlreadyExistsException("Username '" + login.getUserName() + "' already exists.");
        int newLoginId = createUserDAO.createUser(login);
        int userRoleId = roleDAO.findRoleIdByName("user");
        if (userRoleId == -1) throw new ConfigurationException("Default 'user' role not found in the database. Setup is incomplete.");
        userRoleDAO.assignRoleToUser(newLoginId, userRoleId);
    }
}