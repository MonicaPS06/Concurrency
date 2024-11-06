package EnergyManagementSystemProject;

public class BatteryUsageSimulator extends Thread {
    private Battery battery;
    private double usageAmount;
    private String device;
    private boolean isVariableUsage;  // Flag to indicate variable usage behavior
    private volatile boolean running = true; // Control flag for stopping the thread

    
    public BatteryUsageSimulator(Battery battery, double usageAmount, String device, boolean isVariableUsage) {
        this.battery = battery;
        this.usageAmount = usageAmount;
        this.device = device;
        this.isVariableUsage = isVariableUsage; // Store the flag for variable usage
    }
    
    public boolean isAtCapacity() {
        return battery.getCurrentCharge() < usageAmount;
    }

    public void stopUsage() {
        running = false; // Set the flag to stop the thread
    }

    @Override
    public void run() {
        while (running) {
            // Before attempting to use energy, check if the device can operate
            if (battery.getCurrentCharge() == battery.getCapacity()) {
                // Battery is at full capacity
                System.out.printf("[WARNING] %s: Cannot use energy; battery is at full capacity (%.1f units).\n", device, battery.getCurrentCharge());
                // Optionally, you can take other actions, like creating a new instance
                break; // Stop if the battery is full
            }
            else if (!isAtCapacity()) {
                // Use the energy from the battery
                battery.charge(-usageAmount, device, 0); // Negative charge to simulate usage
            } else {
            	 System.out.printf("[INFO] %s: Current charge is sufficient for operation.\n", device);
            } 

            // Sleep to simulate usage interval
            try {
                Thread.sleep(1000); // Adjust as necessary for your simulation timing
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                running = false;
                break; // Break if interrupted
            }
        }
    }
}