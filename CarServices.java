public class CarServices {
    CarNode head;

    public CarServices() {
        this.head = null;
    }

    protected boolean addCar(Car car) {
    
    CarNode curr = head;
    while (curr != null) {
        if (curr.car.getId().equals(car.getId())) {
            System.out.println("Car with ID " + car.getId() + " already exists. Cannot add duplicate.");
            return false; // Duplicate found
        }
        curr = curr.next;
    }

    
    CarNode newNode = new CarNode(car);
    if (head == null) {
        head = newNode;
    } else {
        curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = newNode;
    }

    System.out.println("Car added successfully.");
    return true;
}



    public void displayAllCars() {
        CarNode curr = head;
        while (curr != null) {
            System.out.println(curr.car+" ");
            curr = curr.next;
        }
    }

    public Car findAvailableCar(String brand) {
        CarNode curr = head;
        while (curr != null) {
            if (curr.car.getBrand().equalsIgnoreCase(brand) && curr.car.isAvailable()) {
                return curr.car;
            }
            curr = curr.next;
        }
        return null;
    }
    public void sortCarsByBrand() {
    if (head == null || head.next == null) return;

    CarNode sorted = null;
    CarNode current = head;

    while (current != null) {
        CarNode next = current.next;

        
        if (sorted == null || current.car.getBrand().compareToIgnoreCase(sorted.car.getBrand()) < 0) {
            current.next = sorted;
            sorted = current;
        } else {
            CarNode temp = sorted;
            while (temp.next != null && temp.next.car.getBrand().compareToIgnoreCase(current.car.getBrand()) <= 0) {
                temp = temp.next;
            }
            current.next = temp.next;
            temp.next = current;
        }

        current = next;
    }

    head = sorted;
    System.out.println("Cars sorted by brand name successfully.");
}


    protected boolean deleteCar(String id) {
        CarNode curr = head, prev = null;
        while (curr != null) {
            if (curr.car.getId().equals(id)) {
                if (prev == null) head = curr.next;
                else prev.next = curr.next;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    public Car searchById(String id) {
        CarNode curr = head;
        while (curr != null) {
            if (curr.car.getId().equals(id)) {
                return curr.car;
            }
            curr = curr.next;
        }
        return null;
    }

    public void markAsRented(String id) {
        Car car = searchById(id);
        if (car != null && car.isAvailable()) {
            car.setAvailable(false);
        }
    }

    public void markAsReturned(String id) {
        Car car = searchById(id);
        if (car != null && !car.isAvailable()) {
            car.setAvailable(true);
        }
    }
}
