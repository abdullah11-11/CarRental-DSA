public class PaymentNode {
    Payment payment;
    PaymentNode next;

    public PaymentNode(Payment payment) {
        this.payment = payment;
        this.next = null;
    }
}
