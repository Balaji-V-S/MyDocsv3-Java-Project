package com.mydocs.service;
import com.mydocs.dao.*;
import com.mydocs.exceptions.*;
import com.mydocs.model.Login;
import com.mydocs.model.Role;
import java.util.List;
import java.util.stream.Collectors;

public class AddRolesToUserServiceImpl implements AddRolesToUserService {
    private final FindUserDAO findUserDAO = new FindUserDAOImpl();
    private final RoleDAO roleDAO = new RoleDAOImpl();
    private final UserRoleDAO userRoleDAO = new UserRoleDAOImpl();
    @Override
    public void addRoles(String username, List<String> roleNames) throws UserNotFoundException, RoleNotFoundException, RoleAlreadyExistsException, DataAccessException {
        Login user = findUserDAO.findUserByUsername(username);
        if (user == null) throw new UserNotFoundException("User '" + username + "' not found.");
        List<String> currentRoleNames = roleDAO.findRolesByLoginId(user.getLoginId()).stream().map(Role::getRoleName).collect(Collectors.toList());
        for (String roleName : roleNames) {
            String trimmedRoleName = roleName.trim();
            if (currentRoleNames.contains(trimmedRoleName)) throw new RoleAlreadyExistsException("User '" + username + "' already has the role '" + trimmedRoleName + "'.");
            int roleId = roleDAO.findRoleIdByName(trimmedRoleName);
            if (roleId == -1) throw new RoleNotFoundException("Role '" + trimmedRoleName + "' not found.");
            userRoleDAO.assignRoleToUser(user.getLoginId(), roleId);
        }
    }
}