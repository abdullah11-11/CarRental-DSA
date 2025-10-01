class AdminLogNode {
    String action;
    AdminLogNode next, prev;

    public AdminLogNode(String action) {
        this.action = action;
    }
}
