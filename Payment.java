public class Payment {
    private String paymentId;
    private Bill bill;
    private String paymentDate;
    private boolean isSuccessful;

    public Payment(String paymentId, Bill bill, String paymentDate) {
        this.paymentId = paymentId;
        this.bill = bill;
        this.paymentDate = paymentDate;
        this.isSuccessful = false;
    }

    public String getPaymentId() { return paymentId; }
    public Bill getBill() { return bill; }
    public String getPaymentDate() { return paymentDate; }
    public boolean getIsSuccessful() { return isSuccessful; }

    public void markSuccessful() { this.isSuccessful = true; }
    public void markCancelled() {
            this.isSuccessful = false;
    }

    @Override
    public String toString() {
        return "PaymentID: " + paymentId + "Bill: " + bill.toString() + " Date: " + paymentDate + " Successful: " + isSuccessful;
    }
}
