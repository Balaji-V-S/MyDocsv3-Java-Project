package com.mydocs.dao;

import com.mydocs.exceptions.DataAccessException;
import java.util.List;

public interface FindAllUsernamesDAO {
    List<String> findAllUsernames() throws DataAccessException;
}
