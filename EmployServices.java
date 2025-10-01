public class EmployServices {
    EmployNode head;

    public EmployServices() {
        head = null;
    }

    public boolean register(Employ emp) {
        if (EmpExist(emp.getUsername())) {
            return false;
        }

        EmployNode newEmp = new EmployNode(emp);
        if (head == null) {
            head = newEmp;
            return true;
        }

        EmployNode curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = newEmp;
        return true;
    }

    public boolean EmpExist(String username) {
        EmployNode current = head;
        while (current != null) {
            if (current.employ.getUsername().equals(username)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean login(String userName, String password) {
        EmployNode curr = head;
        while (curr != null) {
            if (curr.employ.getUsername().equals(userName)
                && curr.employ.getPassword().equals(password)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public void updatePerformance(String username, double feedbackRating) {
        EmployNode prev = null, curr = head;

        while (curr != null && !curr.employ.getUsername().equals(username)) {
            prev = curr;
            curr = curr.next;
        }

        if (curr == null)
            return;

        curr.employ.addRating(feedbackRating); 

        // Remove from current position
        if (prev != null) prev.next = curr.next;
        else head = curr.next;

        // Reinsert based on new rating
        insertInPriorityOrder(curr);
    }

    private void insertInPriorityOrder(EmployNode node) {
        if (head == null || head.employ.getRating() < node.employ.getRating()) {
            node.next = head;
            head = node;
            return;
        }

        EmployNode current = head;
        while (current.next != null &&
                current.next.employ.getRating() >= node.employ.getRating()) {
            current = current.next;
        }

        node.next = current.next;
        current.next = node;
    }

    public void displayAdminList() {
        EmployNode current = head;
        while (current != null) {
            System.out.println("Employ: " + current.employ.getUsername() );
            current = current.next;
        }
    }
}
