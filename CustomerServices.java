        import java.util.*;
        public class CustomerServices {
                CustomerNode head;
                Queue<CustomerNode> premium = new LinkedList<>();
                Queue<CustomerNode> normal = new LinkedList<>();

                public CustomerServices(){
                    this.head = null;
                }

                public boolean register(Customer newCustomer){

                    if(userExits(newCustomer.Username)){
                        return false;
                    }
                    CustomerNode newNode = new CustomerNode(newCustomer);
                    if(head == null){
                        head = newNode;
                    }
                    CustomerNode current = head;
                    while(current.next != null){
                        current = current.next;
                    }
                    current.next = newNode;
                    return true;

                }

                public boolean userExits(String userName){
                CustomerNode current = head;
                    while(current != null){
                        if(current.customer.getUsername().equals(userName)){
                            return true;
                        }
                        current = current.next;
                    }
                    return false;



                }

                public boolean login(String userName , String password){
                        CustomerNode current = head;
                        while(current!=null){
                            if(current.customer.getUsername().equals(userName) && current.customer.getPassword().equals(password)){
                                return true;
                            }
                            current = current.next;
                        }
                        return false;

                }

                public void insertCustomerPriorityVise(){
                    premium.clear();
                    normal.clear();
                    CustomerNode curr = head;
                    while (curr !=null ) {
                        if(curr.customer.isVIP()){
                            premium.add(curr);
                        }
                        else{
                            normal.add(curr);
                        }
                        curr=curr.next;
                    }



                }
                public void rateEmploy(String customerId, String employUsername, double rating, EmployServices employServices) {
    CustomerNode curr = head;
    
    
    while (curr != null) {
        if (curr.customer.getId().equals(customerId)) {
            // Let the customer give the rating
            employServices.updatePerformance(employUsername, rating);
            System.out.println("Rating given.");
            return;
        }
        curr = curr.next;
    }

    System.out.println("Customer not found.");
}

public void viewPremiumCustomers() {
    if (premium.isEmpty()) {
        System.out.println("No premium customers at the moment.");
        return;
    }

    System.out.println("======= Premium Customers =======");
    for (CustomerNode node : premium) {
        System.out.println(" Username: " + node.customer.getUsername() + 
                           " | ID: " + node.customer.getId());
    }
    System.out.println("=================================");
}



                public void displayCusList(){
                    CustomerNode curr = head;
                    while(curr != null){
                        System.err.print(curr.customer.getUsername() +"->");
                        curr = curr.next;
                    }
                    System.err.println("null");
                }
            }
