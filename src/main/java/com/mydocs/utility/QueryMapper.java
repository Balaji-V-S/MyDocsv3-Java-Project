package com.mydocs.utility;
public interface QueryMapper {

    // --- Login Table Queries (Primary User Data) ---
    String INSERT_LOGIN = "INSERT INTO login (userName, passWord) VALUES (?, ?)";
    String FIND_LOGIN_BY_USERNAME = "SELECT login_id, userName, passWord FROM login WHERE userName = ?";
    String FIND_LOGIN_ID_BY_USERNAME = "SELECT login_id FROM login WHERE userName = ?";
    String FIND_ALL_USERNAMES = "SELECT userName FROM login";
    String UPDATE_PASSWORD = "UPDATE login SET passWord = ? WHERE login_id = ?";
    String DELETE_LOGIN_BY_ID = "DELETE FROM login WHERE login_id = ?";


    // --- Role Table Queries (Role Definitions) ---
    String FIND_ROLE_ID_BY_NAME = "SELECT role_id FROM role WHERE role_name = ?";


    // --- User_Role Table & Join Queries (Linking Users to Roles) ---
    String FIND_ROLES_BY_LOGIN_ID = "SELECT r.role_id, r.role_name FROM role r JOIN user_role ur ON r.role_id = ur.role_id WHERE ur.login_id = ?";
    String INSERT_USER_ROLE = "INSERT INTO user_role (login_id, role_id) VALUES (?, ?)";
    String DELETE_SPECIFIC_USER_ROLE = "DELETE FROM user_role WHERE login_id = ? AND role_id = ?";
    String DELETE_ALL_USER_ROLES_BY_LOGIN_ID = "DELETE FROM user_role WHERE login_id = ?";
}
