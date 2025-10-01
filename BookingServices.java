import java.util.Stack;

public class BookingServices {
    BookingNode head;
    CarServices carServices;
    CustomerServices customerServices;
     Stack<Booking> bookingStack;  
    PaymentServices paymentServices;

    public BookingServices(CarServices carServices, CustomerServices customerServices) {
        this.head = null;
        this.carServices = carServices;
        this.customerServices = customerServices;
    }

    public boolean bookCar(String bookingId, String customerId, String carId, String date) {
        Car car = carServices.searchById(carId);
        if (car == null || !car.isAvailable()) return false;

        carServices.markAsRented(carId);

        CustomerNode curr = customerServices.head;
        while (curr != null) {
            if (curr.customer.getId().equals(customerId)) {
                curr.customer.incrementBookings();
                break;
            }
            curr = curr.next;
        }

        Booking newBooking = new Booking(bookingId, customerId, carId, date);
        BookingNode newNode = new BookingNode(newBooking);

        if (head == null) head = newNode;
        else {
            BookingNode temp = head;
            while (temp.next != null) temp = temp.next;
            temp.next = newNode;
        }
         bookingStack.push(newBooking); 
        System.out.println("Booking recorded: " + newBooking.getBookingId());

        return true;
    } 
public boolean markAsReturned(String bookingId, String returnDate) {
    BookingNode curr = head;
    while (curr != null) {
        if (curr.booking.getBookingId().equals(bookingId)) {
            // Set return date
            curr.booking.setReturnDate(returnDate);

            // Mark car as available again
            carServices.markAsReturned(curr.booking.getCarId());

            // Find customer and check late return
            CustomerNode custNode = customerServices.head;
            while (custNode != null) {
                if (custNode.customer.getId().equals(curr.booking.getCustomerId())) {
                    boolean isLate = custNode.customer.verifyReturnStatus(curr.booking);
                    return isLate; // This will allow adding penalty in next step
                }
                custNode = custNode.next;
            }
        }
        curr = curr.next;
    }
    return false;
}

   
public void cancelLastBooking() {
        if (!bookingStack.isEmpty()) {
            Booking lastBooking = bookingStack.pop();

            // Mark car as available again
            carServices.markAsReturned(lastBooking.getCarId());

            // Remove booking from linked list
            removeBookingFromList(lastBooking.getBookingId());

            System.out.println("Booking cancelled: " + lastBooking.getBookingId());

            
            paymentServices.cancelLastPayment();
        } else {
            System.out.println("No booking to cancel!");
        }
    }

    // Helper: Remove booking from linked list
    private void removeBookingFromList(String bookingId) {
        if (head == null) return;

        if (head.booking.getBookingId().equals(bookingId)) {
            head = head.next;
            return;
        }

        BookingNode prev = head;
        BookingNode curr = head.next;
        while (curr != null) {
            if (curr.booking.getBookingId().equals(bookingId)) {
                prev.next = curr.next;
                return;
            }
            prev = curr;
            curr = curr.next;
        }
    }

    public void viewAllBookings() {
        BookingNode curr = head;
        while (curr != null) {
            System.out.println(curr.booking);
            curr = curr.next;
        }
    }

    public void viewCustomerBookings(String customerId) {
        BookingNode curr = head;
        while (curr != null) {
            if (curr.booking.getCustomerId().equals(customerId)) {
                System.out.println(curr.booking);
            }
            curr = curr.next;
        }
    }
}
