package com.mydocs.dao;

import com.mydocs.exceptions.DataAccessException;
import com.mydocs.exceptions.DatabaseConnectionException;
import com.mydocs.model.Login;
import com.mydocs.utility.DBConnectionUtil;
import com.mydocs.utility.QueryMapper;
import java.sql.*;

public class FindUserDAOImpl implements FindUserDAO {
    @Override
    public Login findUserByUsername(String username) throws DataAccessException {
        Login login = null;
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(QueryMapper.FIND_LOGIN_BY_USERNAME)) {
            
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    login = new Login();
                    login.setLoginId(rs.getInt("login_id"));
                    login.setUserName(rs.getString("userName"));
                    login.setPassword(rs.getString("passWord"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding user by username: " + e.getMessage(), e);
        }
        return login;
    }

    @Override
    public int findLoginIdByUsername(String username) throws DataAccessException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(QueryMapper.FIND_LOGIN_ID_BY_USERNAME)) {
            
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("login_id");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding login ID by username: " + e.getMessage(), e);
        }
        return -1; // Return -1 if not found
    }
}
