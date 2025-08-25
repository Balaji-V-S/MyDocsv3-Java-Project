package com.mydocs.dao;

import com.mydocs.exceptions.DataAccessException;
import com.mydocs.exceptions.DatabaseConnectionException;
import com.mydocs.model.Login;
import com.mydocs.utility.DBConnectionUtil;
import com.mydocs.utility.QueryMapper;
import java.sql.*;

public class CreateUserDAOImpl implements CreateUserDAO {
    @Override
    public int createUser(Login login) throws DataAccessException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(QueryMapper.INSERT_LOGIN, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, login.getUserName());
            pstmt.setString(2, login.getPassword());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DataAccessException("Creating user failed, no rows affected.", null);
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new DataAccessException("Creating user failed, no ID obtained.", null);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error creating user: " + e.getMessage(), e);
        }
    }
}
