        public class Customer extends USER{
            private int totalBookings;
            private int lateReturns;
            private boolean isVIP;

        public Customer(String id , String Username , String password){

            super(id,  Username, password);
            this.totalBookings = 0;
            this.lateReturns = 0;
            this.isVIP = false;
        }
        public boolean verifyReturnStatus(Booking booking) {//verify Status
    String dueDate = booking.getDueDate();
    String returnDate = booking.getReturnDate();

    if (dueDate == null || returnDate == null) {
        System.out.println("Due date or return date is missing!");
        return false;
    }

    String[] dueParts = dueDate.split("-");
    String[] returnParts = returnDate.split("-");

    int dueYear = Integer.parseInt(dueParts[0]);
    int dueMonth = Integer.parseInt(dueParts[1]);
    int dueDay = Integer.parseInt(dueParts[2]);

    int returnYear = Integer.parseInt(returnParts[0]);
    int returnMonth = Integer.parseInt(returnParts[1]);
    int returnDay = Integer.parseInt(returnParts[2]);

    boolean isLate = false;

    if (returnYear > dueYear) {
        isLate = true;
    } else if (returnYear == dueYear) {
        if (returnMonth > dueMonth) {
            isLate = true;
        } else if (returnMonth == dueMonth && returnDay > dueDay) {
            isLate = true;
        }
    }

    if (isLate) {
        incrementLateReturns();
        System.out.println("Customer returned late! $100 penalty will be applied.");
    } else {
        System.out.println("Customer returned on time.");
    }

    return isLate;
}


        public void incrementLateReturns() {
                lateReturns++;
                checkVIPStatus();}

                public void incrementBookings() {
                totalBookings++;
                checkVIPStatus();
            }

            private void checkVIPStatus() {
                if (totalBookings >= 5 && lateReturns == 0) {
                    this.isVIP = true;
                }
            }


            public boolean isVIP() {
            return isVIP;
        }


        }
