package smartaccesscontrol_icetask4prog;

import java.util.*;
import java.time.*;

class User {
    String username;
    String pin;
    String role;
    
    public User(String username, String pin, String role) {
        this.username = username;
        this.pin= pin;
        this.role = role;
    }
}

public class SmartAccessControl_ICETASK4PROG {
    
    static ArrayList<User> users = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        users.add(new User("admin", "1234", "ADMIN"));

        int attempts = 0;
        boolean locked = false;

        while (!locked) {

            System.out.println("\n=== Smart Access Control Login ===");

            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            User currentUser = authenticate(username, pin);
            
            if (currentUser != null) {
                System.out.println("Login successful!");

                // Timestamp
                System.out.println("Login time: " + LocalDateTime.now());

                // Reset attempts
                attempts = 0;

                // Go to role-based menu
                roleMenu(currentUser);

            } else {
                attempts++;
                System.out.println("Invalid login. Attempts: " + attempts);

                if (attempts >= 3) {
                    System.out.println("System locked due to too many failed attempts.");
                    locked = true;
                } else {
                    try {
                        System.out.println("Waiting 5 seconds before retry...");
                        Thread.sleep(5000); // timeout delay
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    
    }
       
    public static User authenticate(String username, String pin) {
        for (User user : users) {
            if (user.username.equals(username) && user.pin.equals(pin)) {
                return user;
            }
        }
        return null;
    }
    
    public static void roleMenu(User user) {
        boolean running = true;
        
        while (running) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. View Profile");
            System.out.println("2. Logout");

            // Admin gets extra option
            if (user.role.equalsIgnoreCase("ADMIN")) {
                System.out.println("3. Create New User");
            }

            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {

                case 1:
                    System.out.println("Username: " + user.username);
                    System.out.println("Role: " + user.role);
                    break;

                case 2:
                    System.out.println("Logging out...");
                    System.out.println("Logout time: " + LocalDateTime.now());
                    running = false;
                    break;

                case 3:
                    if (user.role.equalsIgnoreCase("ADMIN")) {
                        createUser();
                    } else {
                        System.out.println("Access denied.");
                    }
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    public static void createUser() {
        
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        System.out.print("Enter role (ADMIN / USER): ");
        String role = scanner.nextLine();

        users.add(new User(username, pin, role));

        System.out.println("User created successfully!");
        System.out.println("Timestamp: " + LocalDateTime.now());
    }
    
}
