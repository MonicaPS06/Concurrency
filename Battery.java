package EnergyManagementSystemProject;

public class Battery {
    private double capacity;
    private double currentCharge;

    public Battery(double capacity) {
        this.capacity = capacity;
        this.currentCharge = 0;
    }

    // Synchronized method to ensure thread safety
    public synchronized void charge(double amount, String source) {
        if (currentCharge + amount <= capacity) {
            currentCharge += amount;
            System.out.println(source + " charging: " + amount + " units. Current charge: " + currentCharge);
        } else {
            System.out.println(source + " cannot charge. Battery full at " + currentCharge + "/" + capacity);
        }
    }

    public synchronized double getCurrentCharge() {
        return currentCharge;
    }

    public synchronized double getCapacity() {
        return capacity;
    }
}