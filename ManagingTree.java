public class ManagingTree {
    TreeNode root;

    public void insert(Car car){
        root = insertRecurviely(root,car);
    }

    public TreeNode insertRecurviely(TreeNode root , Car car){
        if (root == null) return new TreeNode(car);

        if(car.getPricePerDay() < 100){
             root.left = insertRecurviely(root.left, car);
        }
             else
            root.right = insertRecurviely(root.right, car);

            return root;
    }


         public void displayCheapCars() {
        System.out.println("Cheap Cars (Price < 100):");
        displayCheapCarsHelper(root);
    }




    private void displayCheapCarsHelper(TreeNode node) {
        if (node == null) return;

        // Only explore left if price could be < 100
        if (node.car.getPricePerDay() < 100) {
            displayCheapCarsHelper(node.left);
            System.out.println(node.car);
            displayCheapCarsHelper(node.right);
        } else {
            // Skip right side since all will be >= 100
            displayCheapCarsHelper(node.left);
        }
    }



         public void displayLuxuryCars() {
        System.out.println("Luxury Cars (Price ≥ 100):");
        displayLuxuryCarsHelper(root);
    }
    


          private void displayLuxuryCarsHelper(TreeNode node) {
        if (node == null) return;

        // Only explore right if price could be ≥ 100
        if (node.car.getPricePerDay() >= 100) {
            displayLuxuryCarsHelper(node.left);
            System.out.println(node.car);
            displayLuxuryCarsHelper(node.right);
        } else {
            // Skip left side since all will be < 100
            displayLuxuryCarsHelper(node.right);
        }
    }


    public void searchByPricePerDay(double targetPrice) {
    System.out.println("Cars with price per day: " + targetPrice);
    searchByPriceHelper(root, targetPrice);
}

private void searchByPriceHelper(TreeNode node, double targetPrice) {
    if (node == null) return;

    // Search in left subtree
    searchByPriceHelper(node.left, targetPrice);

    // Check current node
    if (node.car.getPricePerDay() == targetPrice) {
        System.out.println(node.car);
    }

    // Search in right subtree
    searchByPriceHelper(node.right, targetPrice);
}


}
