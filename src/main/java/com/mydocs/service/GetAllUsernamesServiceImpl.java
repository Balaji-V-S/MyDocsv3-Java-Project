package com.mydocs.service;
import com.mydocs.dao.*;
import com.mydocs.exceptions.DataAccessException;
import java.util.List;

public class GetAllUsernamesServiceImpl implements GetAllUsernamesService {
    private final FindAllUsernamesDAO findAllUsernamesDAO = new FindAllUsernamesDAOImpl();
    @Override
    public List<String> getAll() throws DataAccessException {
        return findAllUsernamesDAO.findAllUsernames();
    }
}