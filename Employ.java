public class Employ extends USER {

    private double rating;         // Average rating
    private int totalFeedbacks;    // Number of feedbacks received

    public Employ(String name, String id, String password) {
        super(id, name, password);
        this.rating = 0.0;
        this.totalFeedbacks = 0;
    }

    public void addRating(double newRating) {
        // Update average rating
        rating = ((rating * totalFeedbacks) + newRating) / (++totalFeedbacks);
    }

    public double getRating() {
        return rating;
    }

    public int getTotalFeedbacks() {
        return totalFeedbacks;
    }

    public void displayStats() {
        System.out.println("Name: " + Username +
            ", Rating: " + String.format("%.2f", rating) +
            ", Feedback Count: " + totalFeedbacks);
    }
}
