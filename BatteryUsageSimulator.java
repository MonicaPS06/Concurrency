package EnergyManagementSystemProject;

public class BatteryUsageSimulator extends Thread {
    private Battery battery;
    private double usageAmount;
    private String device;
    private boolean isVariableUsage;  // Flag to indicate variable usage behavior

    public BatteryUsageSimulator(Battery battery, double usageAmount, String device, boolean isVariableUsage) {
        this.battery = battery;
        this.usageAmount = usageAmount;
        this.device = device;
        this.isVariableUsage = isVariableUsage;
    }

    @Override
    public void run() {
        while (true) {
            double actualUsageAmount = usageAmount;
            
            // Adjust usage amount if variable usage is enabled
            if (isVariableUsage) {
                actualUsageAmount = Math.random() * usageAmount;  // Randomize usage between 0 and usageAmount
            }

            useEnergy(actualUsageAmount);

            // Simulate delay between usage intervals
            try {
                Thread.sleep(100);  // Adjust delay as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void useEnergy(double amount) {
        double currentCharge = battery.getCurrentCharge();
        
        if (currentCharge >= amount) {
            System.out.println(device + " using: " + amount + " units. Current charge after usage: " + (currentCharge - amount));
            battery.charge(-amount, device);  // Negative charge amount to simulate usage
        } else {
            System.out.println(device + " cannot use energy. Insufficient charge: " + currentCharge);
        }
    }
}
