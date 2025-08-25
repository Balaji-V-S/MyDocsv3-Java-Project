package com.mydocs.service;
import com.mydocs.exceptions.*;
import com.mydocs.model.Login;
import java.util.List;

public interface CreateUserWithRolesService {
    void create(Login login, List<String> roleNames) throws InvalidInputException, UsernameAlreadyExistsException, RoleNotFoundException, DataAccessException;
}