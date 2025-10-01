    public class Car {
        private String id;
        private String brand;
        private String model;
        private double pricePerDay;
        private boolean isAvailable;

        public Car(String id, String brand, String model, double pricePerDay) {
            this.id = id;
            this.brand = brand;
            this.model = model;
            this.pricePerDay = pricePerDay;
            this.isAvailable = true;
        }

    
        public String getId() { return id; }
        public String getBrand() { return brand; }
        public String getModel() { return model; }
        public double getPricePerDay() { return pricePerDay; }
        public boolean isAvailable() { return isAvailable; }

        
        public void setAvailable(boolean status) {
            this.isAvailable = status;
        }

       @Override
public String toString() {
    return "ID: " + id + "\n" +
           "Brand: " + brand + "\n" +
           "Model: " + model + "\n" +
           "Price per Day: " + pricePerDay + "\n";
}

    }
