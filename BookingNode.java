public class BookingNode {
    Booking booking;
    BookingNode next;

    public BookingNode(Booking booking) {
        this.booking = booking;
        this.next = null;
    }
}
