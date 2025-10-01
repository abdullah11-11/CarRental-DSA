public class Bill {
    private String bookingId;
    private String customerId;
    private double totalAmount;
    private int rentalDays;

    public Bill(String bookingId, String customerId, double totalAmount, int rentalDays) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.rentalDays = rentalDays;
    }

    public String getBookingId() { return bookingId; }
    public String getCustomerId() { return customerId; }
    public double getTotalAmount() { return totalAmount; }
    public int getRentalDays() { return rentalDays; }

    @Override
    public String toString() {
        return "Bill [BookingID=" + bookingId + ", CustomerID=" + customerId +
               ", Total=" + totalAmount + ", Days=" + rentalDays + "]";
    }
}
