package com.mydocs.dao;

import com.mydocs.exceptions.DataAccessException;
import com.mydocs.exceptions.DatabaseConnectionException;
import com.mydocs.utility.DBConnectionUtil;
import com.mydocs.utility.QueryMapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FindAllUsernamesDAOImpl implements FindAllUsernamesDAO {
    @Override
    public List<String> findAllUsernames() throws DataAccessException {
        List<String> usernames = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(QueryMapper.FIND_ALL_USERNAMES);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                usernames.add(rs.getString("userName"));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all usernames: " + e.getMessage(), e);
        }
        return usernames;
    }
}
