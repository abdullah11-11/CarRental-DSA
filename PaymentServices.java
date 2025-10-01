import java.util.Stack;

public class PaymentServices {
    PaymentNode head;
    BookingServices bookingServices;
    CustomerServices customerServices;
     Stack<Payment> paymentStack; 

    public PaymentServices(BookingServices bookingServices, CustomerServices customerServices) {
        this.head = null;
        this.bookingServices = bookingServices;
        this.customerServices = customerServices;
        this.paymentStack = new Stack<>();
    }

    public boolean recordPayment(String paymentId, Bill bill, String paymentDate) {
    

    Payment newPayment = new Payment(paymentId, bill, paymentDate);
    newPayment.markSuccessful();
    PaymentNode newNode = new PaymentNode(newPayment);

    if (head == null) head = newNode;
    else {
        PaymentNode temp = head;
        while (temp.next != null) temp = temp.next;
        temp.next = newNode;
    }
        paymentStack.push(newPayment);

        System.out.println("Payment recorded: " + newPayment);

    return true;
}
public void cancelLastPayment() {
        if (!paymentStack.isEmpty()) {
            
            Payment lastPayment = paymentStack.pop();

          
            lastPayment.markCancelled();

            System.out.println("Cancelled payment: " + lastPayment.getPaymentId());
        } else {
            System.out.println("No payment to cancel!");
        }
    }
   public void viewAllPayments() {
    PaymentNode curr = head;
    while (curr != null) {
        System.out.println("=== Payment Details ===");
        System.out.println(curr.payment);  
        System.out.println("=== Bill Details ===");
        System.out.println(curr.payment.getBill());  
        System.out.println();
        curr = curr.next;
    }
}

}
