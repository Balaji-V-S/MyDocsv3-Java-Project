package com.mydocs.dao;

import com.mydocs.exceptions.DataAccessException;
import com.mydocs.exceptions.DatabaseConnectionException;
import com.mydocs.utility.DBConnectionUtil;
import com.mydocs.utility.QueryMapper;
import java.sql.*;

public class UserRoleDAOImpl implements UserRoleDAO {
    @Override
    public void assignRoleToUser(int loginId, int roleId) throws DataAccessException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(QueryMapper.INSERT_USER_ROLE)) {

            pstmt.setInt(1, loginId);
            pstmt.setInt(2, roleId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            if (e instanceof SQLException && ((SQLException) e).getErrorCode() == 1062) {
                // Let the service layer handle duplicate role assignments.
            } else {
                throw new DataAccessException("Error assigning role to user: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public boolean removeRoleFromUser(int loginId, int roleId) throws DataAccessException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(QueryMapper.DELETE_SPECIFIC_USER_ROLE)) {

            pstmt.setInt(1, loginId);
            pstmt.setInt(2, roleId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error removing role from user: " + e.getMessage(), e);
        }
    }

    /**
     * NEW METHOD IMPLEMENTATION
     */
    @Override
    public void deleteAllRolesByLoginId(int loginId) throws DataAccessException {
        // This query comes from your QueryMapper.java file
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(QueryMapper.DELETE_ALL_USER_ROLES_BY_LOGIN_ID)) {

            pstmt.setInt(1, loginId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting user roles by login ID: " + e.getMessage(), e);
        }
    }
}
