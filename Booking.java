public class Booking {
    private String bookingId;
    private String customerId;
    private String carId;
    private String bookingDate;
    private String dueDate;       // new
    private String returnDate;    // new

    public Booking(String bookingId, String customerId, String carId, String bookingDate) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.carId = carId;
        this.bookingDate = bookingDate;
        // Default: due date after 5 days of booking (for simplicity)
        this.dueDate = calculateDueDate(bookingDate);
        this.returnDate = null;
    }

    private String calculateDueDate(String bookingDate) {
        // For now: just add 5 days manually (no date API for simplicity)
        String[] parts = bookingDate.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        day += 5;
        return year + "-" + month + "-" + day;
    }

    public String getDueDate() { return dueDate; }
    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
    public String getCustomerId() { return customerId; }
    public String getCarId() { return carId; }
    public String getBookingId() {return bookingId;}
}
