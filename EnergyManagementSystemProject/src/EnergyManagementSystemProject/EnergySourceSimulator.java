package EnergyManagementSystemProject;

public class EnergySourceSimulator extends Thread {
    private Battery battery;
    private double chargeAmount;
    private String source;
    private volatile boolean running = true; // Control flag for stopping the thread

    public EnergySourceSimulator(Battery battery, double chargeAmount, String source) {
        this.battery = battery;
        this.chargeAmount = chargeAmount;
        this.source = source;
    }

    public void stopCharging() {
        System.out.println(source + " is stopping charging.");
        running = false; // Method to stop charging
    }

    @Override
    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            if (battery.getCurrentCharge() < battery.getCapacity()) {
                // Charge the battery
                battery.charge(chargeAmount, source, 0); // Pass the source name for logging
            } else {
            	System.out.printf("[INFOM] %s: Battery full at %.1f units. Stopping charging.\n\n", source, battery.getCurrentCharge());
            	stopCharging();
                break; // Exit the loop if the battery is full
            }

            // Sleep to simulate charging interval
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
                break; // Exit the loop if interrupted
            }
        }
        System.out.printf("[INFO] %s: Stopped charging.\n\n", source);
    }
}
