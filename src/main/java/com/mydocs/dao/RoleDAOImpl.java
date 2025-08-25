package com.mydocs.dao;

import com.mydocs.exceptions.DataAccessException;
import com.mydocs.model.Role;
import com.mydocs.utility.DBConnectionUtil;
import com.mydocs.utility.QueryMapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {
    @Override
    public List<Role> findRolesByLoginId(int loginId) throws DataAccessException {
        List<Role> roles = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(QueryMapper.FIND_ROLES_BY_LOGIN_ID)) {
            
            pstmt.setInt(1, loginId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    roles.add(new Role(rs.getInt("role_id"), rs.getString("role_name")));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding roles by login ID: " + e.getMessage(), e);
        }
        return roles;
    }

    @Override
    public int findRoleIdByName(String roleName) throws DataAccessException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(QueryMapper.FIND_ROLE_ID_BY_NAME)) {
            
            pstmt.setString(1, roleName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("role_id");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding role ID by name: " + e.getMessage(), e);
        }
        return -1; // Return -1 if not found
    }
}
