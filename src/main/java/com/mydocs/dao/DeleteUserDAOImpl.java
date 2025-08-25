package com.mydocs.dao;

import com.mydocs.exceptions.DataAccessException;
import com.mydocs.exceptions.DatabaseConnectionException;
import com.mydocs.utility.DBConnectionUtil;
import com.mydocs.utility.QueryMapper;
import java.sql.*;

public class DeleteUserDAOImpl implements DeleteUserDAO {
    @Override
    public boolean deleteUserById(int loginId) throws DataAccessException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(QueryMapper.DELETE_LOGIN_BY_ID)) {
            
            pstmt.setInt(1, loginId);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting user by ID: " + e.getMessage(), e);
        }
    }
}
