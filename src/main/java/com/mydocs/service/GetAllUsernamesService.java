package com.mydocs.service;
import com.mydocs.exceptions.DataAccessException;
import java.util.List;
public interface GetAllUsernamesService {
    List<String> getAll() throws DataAccessException;
}