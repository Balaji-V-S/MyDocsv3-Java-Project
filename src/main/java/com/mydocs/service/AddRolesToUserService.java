package com.mydocs.service;
import com.mydocs.exceptions.*;
import java.util.List;

public interface AddRolesToUserService {
    void addRoles(String username, List<String> roleNames) throws UserNotFoundException, RoleNotFoundException, RoleAlreadyExistsException, DataAccessException;
}