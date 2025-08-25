package com.mydocs.service;
import com.mydocs.exceptions.*;

public interface RemoveAdminRoleService {
    boolean removeAdmin(String username) throws UserNotFoundException, ConfigurationException, DataAccessException;
}