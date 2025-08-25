package com.mydocs.service;
import com.mydocs.dao.*;
import com.mydocs.exceptions.*;
import com.mydocs.model.Login;
import java.util.List;

public class CreateUserWithRolesServiceImpl implements CreateUserWithRolesService {
    private final FindUserDAO findUserDAO = new FindUserDAOImpl();
    private final CreateUserDAO createUserDAO = new CreateUserDAOImpl();
    private final RoleDAO roleDAO = new RoleDAOImpl();
    private final UserRoleDAO userRoleDAO = new UserRoleDAOImpl();
    @Override
    public void create(Login login, List<String> roleNames) throws InvalidInputException, UsernameAlreadyExistsException, RoleNotFoundException, DataAccessException {
        if (login.getUserName() == null || login.getUserName().trim().isEmpty()) throw new InvalidInputException("Username cannot be empty.");
        if (login.getPassword() == null || login.getPassword().isEmpty()) throw new InvalidInputException("Password cannot be empty.");
        if (findUserDAO.findUserByUsername(login.getUserName()) != null) throw new UsernameAlreadyExistsException("Username '" + login.getUserName() + "' already exists.");
        int newLoginId = createUserDAO.createUser(login);
        for (String roleName : roleNames) {
            int roleId = roleDAO.findRoleIdByName(roleName.trim());
            if (roleId == -1) throw new RoleNotFoundException("Role '" + roleName + "' not found. User creation partially failed.");
            userRoleDAO.assignRoleToUser(newLoginId, roleId);
        }
    }
}