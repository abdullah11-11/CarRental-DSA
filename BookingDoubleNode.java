public class BookingDoubleNode {
    Booking booking;
    BookingDoubleNode prev;
    BookingDoubleNode next;


    public BookingDoubleNode(Booking booking){
        this.booking = booking;
        this.prev = null;
        this.next = null;
    }
}
