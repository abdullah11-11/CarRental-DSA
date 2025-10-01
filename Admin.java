public class Admin {

    AdminLogNode head, tail;

    private final String username = "admin";
    private final String password = "123456";

    // Admin Login
    public boolean login(String inputPassword) {
        return password.equals(inputPassword);
    }

    // Add Car with Logging
    public void addCar(CarServices carServices, Car newCar) {
    if (carServices.addCar(newCar)) {
        logAction("Added car: " + newCar.getId());
    } else {
        logAction("Failed to add car: " + newCar.getId() + " (duplicate ID)");
    }
}


    // Delete Car with Logging
    public void deleteCar(CarServices carServices, String carId) {
        boolean removed = carServices.deleteCar(carId);
        if (removed)
            logAction("Deleted car: " + carId);
        else
            logAction("Failed to delete car: " + carId + " (not found)");
    }

    // Register Employee with Logging
    public boolean registerEmploy(EmployServices employServices, Employ newEmploy) {
        boolean added = employServices.register(newEmploy);
        if (added)
            logAction("Registered employee: " + newEmploy.getUsername());
        else
            logAction("Employee already exists: " + newEmploy.getUsername());
        return added;
    }

    // Insert Customer into Priority Queue
    public void addCusPriorityVise(CustomerServices customerServices) {
        customerServices.insertCustomerPriorityVise();
        logAction("Updated customer queues by priority.");
    }

    // Add Admin Log Entry
    public void logAction(String action) {
        AdminLogNode node = new AdminLogNode(action);
        node.next = head;
        if (head != null) head.prev = node;
        head = node;
        if (tail == null) tail = node;
    }

    // View All Admin Log Entries
    public void viewLogs() {
        AdminLogNode curr = head;
        while (curr != null) {
            System.out.println(curr.action);
            curr = curr.next;
        }
    }
}
