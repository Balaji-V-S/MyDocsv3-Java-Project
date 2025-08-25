package com.mydocs.ui;

import com.mydocs.exceptions.*;
import com.mydocs.model.Login;
import com.mydocs.model.Role;
import com.mydocs.service.*;
import com.mydocs.utility.DBConnectionUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Main UI class for the console application.
 * Handles all user interaction and exception display using a granular service layer.
 */
public class TestScript {

    private static Login loggedInUser = null;

    public static void main(String[] args) {
        // Using try-with-resources ensures the scanner is always closed properly
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                if (loggedInUser == null) {
                    showMainMenu(scanner);
                } else {
                    // Check for authorization before showing menus
                    if (isAdmin(loggedInUser)) {
                        showAdminMenu(scanner);
                    } else {
                        showUserMenu(scanner);
                    }
                }
            }
        } finally {
            System.out.println("\nApplication has shut down.");
        }
    }

    private static void showMainMenu(Scanner scanner) {
        System.out.println("\n--- Welcome ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int choice = getIntegerInput(scanner);
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1: handleLogin(scanner); break;
            case 2: handleRegister(scanner); break;
            case 3: System.exit(0);
            default: System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void handleLogin(Scanner scanner) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            validateUsernameInput(username); // Validate for spaces

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            // Instantiate only the LoginService for this specific operation
            LoginService loginService = new LoginServiceImpl();
            loggedInUser = loginService.login(username.trim(), password);

            System.out.println("Login successful! Welcome, " + loggedInUser.getUserName());
        } catch (UserNotFoundException | AuthenticationException e) {
            System.err.println("Login failed: Invalid username or password.");
        } catch (InvalidInputException | DataAccessException e) {
            System.err.println("Login failed: " + e.getMessage());
        }
    }

    private static void handleRegister(Scanner scanner) {
        try {
            System.out.print("Enter new username: ");
            String username = scanner.nextLine();
            validateUsernameInput(username); // Validate for spaces

            System.out.print("Enter new password: ");
            String password = scanner.nextLine();

            // Instantiate only the RegisterUserService for this specific operation
            RegisterUserService registerService = new RegisterUserServiceImpl();
            registerService.register(new Login(username, password));

            System.out.println("Registration successful! Please log in.");
        } catch (InvalidInputException | UsernameAlreadyExistsException | ConfigurationException e) {
            System.err.println("Registration failed: " + e.getMessage());
        } catch (DataAccessException e) {
            System.err.println("Registration failed due to a database error: " + e.getMessage());
        }
    }

    private static void showAdminMenu(Scanner scanner) {
        System.out.println("\n--- Admin Dashboard (Logged in as: " + loggedInUser.getUserName() + ") ---");
        System.out.println("1. Create a new user");
        System.out.println("2. Retrieve all usernames");
        System.out.println("3. Add roles to an existing user");
        System.out.println("4. Delete a user");
        System.out.println("5. Remove Admin role from a user");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
        int choice = getIntegerInput(scanner);
        scanner.nextLine();
        handleAdminActions(choice, scanner);
    }

    private static void handleAdminActions(int choice, Scanner scanner) {
        try {
            switch (choice) {
                case 1:
                    System.out.print("Enter new username: ");
                    String newUser = scanner.nextLine();
                    validateUsernameInput(newUser); // Validate for spaces

                    System.out.print("Enter new password: ");
                    String newPass = scanner.nextLine();
                    System.out.print("Enter roles (comma-separated): ");
                    List<String> roles = Arrays.stream(scanner.nextLine().split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
                    if (roles.isEmpty()) roles.add("user");
                    // Instantiate and call the specific service for this action
                    new CreateUserWithRolesServiceImpl().create(new Login(newUser, newPass), roles);
                    System.out.println("User '" + newUser + "' created successfully.");
                    break;
                case 2:
                    System.out.println("\n--- All Usernames ---");
                    // Instantiate and call the specific service
                    new GetAllUsernamesServiceImpl().getAll().forEach(System.out::println);
                    System.out.println("---------------------");
                    break;
                case 3:
                    System.out.print("Enter username to edit: ");
                    String userToEdit = scanner.nextLine();
                    validateUsernameInput(userToEdit); // Validate for spaces

                    System.out.print("Enter new roles to add (comma-separated): ");
                    List<String> rolesToAdd = Arrays.stream(scanner.nextLine().split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
                    if (rolesToAdd.isEmpty()) throw new InvalidInputException("No roles provided.");
                    // Instantiate and call the specific service
                    new AddRolesToUserServiceImpl().addRoles(userToEdit.trim(), rolesToAdd);
                    System.out.println("Successfully added roles to user '" + userToEdit.trim() + "'.");
                    break;
                case 4:
                    System.out.print("Enter username to delete: ");
                    String userToDelete = scanner.nextLine();
                    validateUsernameInput(userToDelete); // Validate for spaces

                    if (userToDelete.trim().equalsIgnoreCase(loggedInUser.getUserName())) throw new OperationNotPermittedException("Admins cannot delete their own account.");
                    System.out.print("Are you sure? (yes/no): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                        // Instantiate and call the specific service
                        new DeleteUserServiceImpl().delete(userToDelete.trim());
                        System.out.println("User deleted successfully.");
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                    break;
                case 5:
                    System.out.print("Enter username to remove admin role from: ");
                    String userToDemote = scanner.nextLine();
                    validateUsernameInput(userToDemote); // Validate for spaces

                    if (userToDemote.trim().equalsIgnoreCase(loggedInUser.getUserName())) throw new OperationNotPermittedException("Cannot remove your own admin role.");
                    // Instantiate and call the specific service
                    new RemoveAdminRoleServiceImpl().removeAdmin(userToDemote.trim());
                    System.out.println("Admin role removed from user '" + userToDemote.trim() + "'.");
                    break;
                case 6:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            // This single catch block gracefully handles all specific exceptions from the service layer
            System.err.println("Operation failed: " + e.getMessage());
        }
    }

    private static void showUserMenu(Scanner scanner) {
        System.out.println("\n--- User Dashboard (Logged in as: " + loggedInUser.getUserName() + ") ---");
        System.out.println("1. Delete my account");
        System.out.println("2. Logout");
        System.out.print("Enter your choice: ");
        int choice = getIntegerInput(scanner);
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Are you sure? This is permanent. (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                try {
                    new DeleteUserServiceImpl().delete(loggedInUser.getUserName());
                    System.out.println("Your account has been deleted.");
                    logout();
                } catch (Exception e) {
                    System.err.println("Failed to delete account: " + e.getMessage());
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else if (choice == 2) {
            logout();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void logout() {
        System.out.println(loggedInUser.getUserName() + " has been logged out.");
        loggedInUser = null;
    }

    private static boolean isAdmin(Login user) {
        if (user == null || user.getRoles() == null) {
            return false;
        }
        return user.getRoles().stream().anyMatch(role -> "admin".equalsIgnoreCase(role.getRoleName()));
    }

    private static int getIntegerInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void validateUsernameInput(String username) throws InvalidInputException {
        if (username != null && username.contains(" ")) {
            throw new InvalidInputException("Username cannot contain blank spaces. Please try again.");
        }
    }
}
