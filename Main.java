import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Admin admin = new Admin();
        CarServices carServices = new CarServices();
        EmployServices employServices = new EmployServices();
        CustomerServices customerServices = new CustomerServices();
        BookingServices bookingServices = new BookingServices(carServices, customerServices);
        PaymentServices paymentServices = new PaymentServices(bookingServices, customerServices);
        bookingServices.paymentServices = paymentServices;
        TotalBillCalculator billCalculator = new TotalBillCalculator(carServices, bookingServices);
        ManagingTree carTree = new ManagingTree();

        boolean exit = false;

        while (!exit) {
            int choice = getMenuChoice(
                    "\n--- Welcome to Car Rental System ---\nSelect your role:",
                    new String[]{"Admin", "Employee", "Customer", "Exit"}
            );

            switch (choice) {
                case 1 -> adminMenu(admin, carServices, employServices, customerServices, bookingServices, carTree);
                case 2 -> employeeMenu(employServices, billCalculator, bookingServices, paymentServices);
                case 3 -> customerMenu(customerServices, carServices, bookingServices);
                case 4 -> {
                    System.out.println("==============================================");
                    System.out.println("   Thank you for using the Car Rental System!");
                    System.out.println("        Made by: Abdullah, Ahmad, Usama,");
                    System.out.println("                 Fozan, Ahmad Aziz");
                    System.out.println("==============================================");
                    exit = true;
                }
            }
        }
    }

    // ------------------------ ADMIN MENU ------------------------
    private static void adminMenu(Admin admin, CarServices carServices, EmployServices employServices,
                                  CustomerServices customerServices, BookingServices bookingServices,
                                  ManagingTree carTree) {
        while (true) {
            System.out.print("Enter Admin Password (or 'back' to main menu): ");
            String password = sc.nextLine().trim();
            if (password.equalsIgnoreCase("back")) return;
            if (admin.login(password)) break;
            System.out.println("Incorrect password. Try again.");
        }

        System.out.println("Admin login successful!");

        boolean adminExit = false;
        while (!adminExit) {
            int adminChoice = getMenuChoice(
                    "\n===== Admin Menu =====",
                    new String[]{
                            "Add Car", "Delete Car", "Register Employee", "View Admin Logs",
                            "View All Cars", "Sort Cars by Brand", "Insert Customers by Priority",
                            "Insert Car into Tree", "View Cheap Cars (< 100)", "View Luxury Cars (≥ 100)",
                            "Search Car by Price", "View Booking History", "View Premium Customers", "Logout"
                    }
            );

            switch (adminChoice) {
                case 1 -> {
                    String carId = getNonEmptyInput("Enter Car ID: ");
                    if (carServices.searchById(carId) != null) {
                        System.out.println("Car with this ID already exists.");
                        break;
                    }
                    String brand = getNonEmptyInput("Enter Brand: ");
                    String model = getNonEmptyInput("Enter Model: ");
                    double price = getDoubleInput("Enter Price Per Day: ");
                    Car newCar = new Car(carId, brand, model, price);
                    admin.addCar(carServices, newCar);
                    admin.logAction("Added Car: " + carId);
                }
                case 2 -> {
                    String delId = getNonEmptyInput("Enter Car ID to delete: ");
                    admin.deleteCar(carServices, delId);
                    admin.logAction("Deleted Car: " + delId);
                }
                case 3 -> {
                    String empId = getNonEmptyInput("Enter Employee ID: ");
                    String empUser = getNonEmptyInput("Enter Employee Username: ");
                    String empPass = getNonEmptyInput("Enter Password: ");
                    Employ newEmp = new Employ(empUser, empId, empPass);
                    admin.registerEmploy(employServices, newEmp);
                    admin.logAction("Registered Employee: " + empUser);
                    System.out.println("Employee registered.");
                }
                case 4 -> admin.viewLogs();
                case 5 -> carServices.displayAllCars();
                case 6 -> carServices.sortCarsByBrand();
                case 7 -> {
                    customerServices.insertCustomerPriorityVise();
                    admin.logAction("Inserted Customers by Priority");
                }
                case 8 -> {
                    String treeCarId = getNonEmptyInput("Enter Car ID: ");
                    String treeBrand = getNonEmptyInput("Enter Brand: ");
                    String treeModel = getNonEmptyInput("Enter Model: ");
                    double treePrice = getDoubleInput("Enter Price Per Day: ");
                    carTree.insert(new Car(treeCarId, treeBrand, treeModel, treePrice));
                    admin.logAction("Inserted Car into Tree: " + treeCarId);
                }
                case 9 -> carTree.displayCheapCars();
                case 10 -> carTree.displayLuxuryCars();
                case 11 -> {
                    double searchPrice = getDoubleInput("Enter Price to Search: ");
                    carTree.searchByPricePerDay(searchPrice);
                }
                case 12 -> bookingServices.viewAllBookings();
                case 13 -> {
                    customerServices.insertCustomerPriorityVise();
                    customerServices.viewPremiumCustomers();
                }
                case 14 -> adminExit = true;
            }
        }
    }

    // ------------------------ EMPLOYEE MENU ------------------------
    private static void employeeMenu(EmployServices employServices, TotalBillCalculator billCalculator,
                                     BookingServices bookingServices, PaymentServices paymentServices) {
        while (true) {
            String empUsername = getNonEmptyInputAllowBack("Enter Username (or 'back' to main menu): ");
            if (empUsername == null) return;
            String empPassword = getNonEmptyInputAllowBack("Enter Password (or 'back' to main menu): ");
            if (empPassword == null) return;
            if (employServices.login(empUsername, empPassword)) break;
            System.out.println("Login failed. Try again.");
        }

        System.out.println("Employee login successful!");

        boolean empExit = false;
        while (!empExit) {
            int empChoice = getMenuChoice(
                    "\n===== Employee Menu =====",
                    new String[]{"Calculate Bill by Booking ID", "Calculate Bill by Customer ID",
                            "View All Bookings", "View All Payments", "Logout"}
            );

            switch (empChoice) {
                case 1 -> {
                    String bId = getNonEmptyInputAllowBack("Enter Booking ID (or 'back' to cancel): ");
                    if (bId == null) break;
                    int days = (int) getDoubleInput("Enter Rental Days: ");
                    double pen = getDoubleInput("Enter Penalty (if any): ");
                    System.out.println("Total Bill: $" + billCalculator.calculateBillByBooking(bId, days, pen));
                }
                case 2 -> {
                    String custId = getNonEmptyInputAllowBack("Enter Customer ID (or 'back' to cancel): ");
                    if (custId == null) break;
                    int d = (int) getDoubleInput("Enter Rental Days per Booking: ");
                    double p = getDoubleInput("Enter Total Penalty: ");
                    System.out.println("Total Bill: $" + billCalculator.calculateBillByCustomer(custId, d, p));
                }
                case 3 -> bookingServices.viewAllBookings();
                case 4 -> paymentServices.viewAllPayments();
                case 5 -> empExit = true;
            }
        }
    }

    // ------------------------ CUSTOMER MENU ------------------------
    private static void customerMenu(CustomerServices customerServices, CarServices carServices,
                                     BookingServices bookingServices) {
        int sub = getMenuChoice("Customer Menu:", new String[]{"Register", "Login"});
        String username = null;

        if (sub == 1) {
            String id = getNonEmptyInput("Enter ID: ");
            username = getNonEmptyInput("Enter Username: ");
            String pass = getNonEmptyInput("Enter Password: ");
            if (customerServices.register(new Customer(id, username, pass)))
                System.out.println("Registration successful!");
            else {
                System.out.println("Username already exists!");
                return;
            }
        } else {
            while (true) {
                username = getNonEmptyInputAllowBack("Enter Username (or 'back' to main menu): ");
                if (username == null) return;
                String pass = getNonEmptyInputAllowBack("Enter Password (or 'back' to main menu): ");
                if (pass == null) return;
                if (customerServices.login(username, pass)) break;
                System.out.println("Login failed. Try again.");
            }
        }

        System.out.println("Customer login successful!");

        boolean customerExit = false;
        while (!customerExit) {
            int customerChoice = getMenuChoice(
                    "\n===== Customer Menu =====",
                    new String[]{"Book a Car", "Cancel Last Booking", "View My Booking History",
                            "Rate an Employee", "Return a Car", "View All Cars",
                            "Sort Cars by Brand", "Find Available Car by Brand", "Logout"}
            );

            switch (customerChoice) {
                case 1 -> {
                    while (true) {
                        String bookingId = getNonEmptyInputAllowBack("Enter Booking ID (or 'back' to cancel): ");
                        if (bookingId == null) break; // go back to customer menu
                        String carId = getNonEmptyInputAllowBack("Enter Car ID (or 'back' to cancel): ");
                        if (carId == null) break;
                        String date = getValidDateAllowBack("Enter Booking Date (YYYY-MM-DD) (or 'back' to cancel): ");
                        if (date == null) break;
                        if (bookingServices.bookCar(bookingId, username, carId, date)) {
                            System.out.println("Car booked successfully!");
                            break;
                        }
                        System.out.println("Booking failed. Try again.");
                    }
                }
                case 2 -> bookingServices.cancelLastBooking();
                case 3 -> bookingServices.viewCustomerBookings(username);
                case 4 -> {
                    String empUser = getNonEmptyInput("Enter Employee Username: ");
                    double rating = getDoubleInput("Enter Rating (0.0 - 5.0): ");
                    customerServices.rateEmploy(username, empUser, rating, null);
                }
                case 5 -> {
                    String returnBookingId = getNonEmptyInputAllowBack("Enter Booking ID to return (or 'back' to cancel): ");
                    if (returnBookingId == null) break;
                    String returnDate = getValidDateAllowBack("Enter Return Date (YYYY-MM-DD) (or 'back' to cancel): ");
                    if (returnDate == null) break;
                    if (bookingServices.markAsReturned(returnBookingId, returnDate))
                        System.out.println("Return was late. A penalty will be added.");
                    else
                        System.out.println("Return recorded successfully.");
                }
                case 6 -> carServices.displayAllCars();
                case 7 -> {
                    carServices.sortCarsByBrand();
                    carServices.displayAllCars();
                }
                case 8 -> {
                    String searchBrand = getNonEmptyInput("Enter brand name to search available car: ");
                    Car foundCar = carServices.findAvailableCar(searchBrand);
                    if (foundCar != null) System.out.println("Available car found:\n" + foundCar);
                    else System.out.println("No available car found with that brand.");
                }
                case 9 -> customerExit = true;
            }
        }
    }

    // ------------------------ HELPER METHODS ------------------------

    private static int getMenuChoice(String title, String[] options) {
        int choice;
        do {
            System.out.println("\n" + title);
            for (int i = 0; i < options.length; i++)
                System.out.println((i + 1) + ". " + options[i]);
            System.out.print("Enter your choice: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and " + options.length + ".");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();
        } while (choice < 1 || choice > options.length);
        return choice;
    }

    private static String getNonEmptyInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (input.isEmpty()) System.out.println("Input cannot be empty.");
        } while (input.isEmpty());
        return input;
    }

    /**
     * Like getNonEmptyInput but returns null if user types "back"
     */
    private static String getNonEmptyInputAllowBack(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("back")) return null;
            if (!input.isEmpty()) return input;
            System.out.println("Input cannot be empty.");
        }
    }

    private static double getDoubleInput(String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                value = sc.nextDouble();
                sc.nextLine();
                return value;
            } else {
                System.out.println("Invalid number. Try again.");
                sc.next();
            }
        }
    }

    /**
     * Like getValidDate but returns null if user types "back"
     */
    private static String getValidDateAllowBack(String prompt) {
        while (true) {
            String date = getNonEmptyInputAllowBack(prompt);
            if (date == null) return null;
            try {
                LocalDate.parse(date);
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }
}
