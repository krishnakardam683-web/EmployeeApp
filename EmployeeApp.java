import java.sql.*;
import java.util.Scanner;

public class EmployeeApp {
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/employee_db";
    static final String USER = "root";
    static final String PASS = "password";
    static Connection conn;

    public static void connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(JDBC_URL, USER, PASS);
    }

    // Create Employee
    public static void createEmployee(int id, String name, double salary) {
        String query = "INSERT INTO employees (id, name, salary) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, salary);
            ps.executeUpdate();
            System.out.println(" Employee added successfully!");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(" Error: Employee ID already exists!");
        } catch (SQLException e) {
            System.out.println(" Error adding employee: " + e.getMessage());
        }
    }

    // Read Employees
    public static void readEmployees() {
        String query = "SELECT * FROM employees";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n Employee Records:");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("ID: " + rs.getInt("id") +
                                   ", Name: " + rs.getString("name") +
                                   ", Salary: " + rs.getDouble("salary"));
            }
            if (!found) {
                System.out.println(" No employee records found.");
            }
        } catch (SQLException e) {
            System.out.println(" Error reading employees: " + e.getMessage());
        }
    }

    // Update Employee
    public static void updateEmployee(int id, String newName, double newSalary) {
        String query = "UPDATE employees SET name=?, salary=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, newName);
            ps.setDouble(2, newSalary);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println(" Employee updated successfully!");
            } else {
                System.out.println(" Employee not found!");
            }
        } catch (SQLException e) {
            System.out.println(" Error updating employee: " + e.getMessage());
        }
    }

    // Delete Employee
    public static void deleteEmployee(int id) {
        String query = "DELETE FROM employees WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println(" Employee deleted successfully!");
            } else {
                System.out.println(" Employee not found!");
            }
        } catch (SQLException e) {
            System.out.println(" Error deleting employee: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            connect();
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n==== EMPLOYEE DATABASE MENU ====");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Salary: ");
                        double salary = sc.nextDouble();
                        createEmployee(id, name, salary);
                        break;

                    case 2:
                        readEmployees();
                        break;

                    case 3:
                        System.out.print("Enter Employee ID to update: ");
                        int uid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter New Name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter New Salary: ");
                        double newSalary = sc.nextDouble();
                        updateEmployee(uid, newName, newSalary);
                        break;

                    case 4:
                        System.out.print("Enter Employee ID to delete: ");
                        int did = sc.nextInt();
                        deleteEmployee(did);
                        break;

                    case 5:
                        System.out.println(" Exiting...");
                        sc.close();
                        conn.close();
                        return;

                    default:
                        System.out.println(" Invalid choice! Try again.");
                }
            }

        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }
}