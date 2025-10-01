public class TotalBillCalculator {
    private CarServices carServices;
    private BookingServices bookingServices;

    public TotalBillCalculator(CarServices carServices, BookingServices bookingServices) {
        this.carServices = carServices;
        this.bookingServices = bookingServices;
    }

    public double calculateBillByBooking(String bookingId, int rentalDays, double penalty) { // <-- Added penalty parameter
    BookingNode curr = bookingServices.head;
    while (curr != null) {
        if (curr.booking.getBookingId().equals(bookingId)) {
            Car car = carServices.searchById(curr.booking.getCarId());
            if (car != null) {
                // Added penalty to the total bill
                return (car.getPricePerDay() * rentalDays) + penalty; // <-- Added penalty
            }
        }
        curr = curr.next;
    }
    return 0.0;
}


    public double calculateBillByCustomer(String customerId, int rentalDaysPerBooking, double totalPenalty) { // <-- Added totalPenalty parameter
    double total = 0.0;
    BookingNode curr = bookingServices.head;
    while (curr != null) {
        if (curr.booking.getCustomerId().equals(customerId)) {
            Car car = carServices.searchById(curr.booking.getCarId());
            if (car != null) {
                total += car.getPricePerDay() * rentalDaysPerBooking;
            }
        }
        curr = curr.next;
    }
    return total + totalPenalty; // <-- Added penalty to final total
}

}
