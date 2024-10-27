package EnergyManagementSystemProject;

public class EnergySourceSimulator extends Thread {
    private Battery battery;
    private double chargeAmount;
    private String source;

    public EnergySourceSimulator(Battery battery, double chargeAmount, String source) {
        this.battery = battery;
        this.chargeAmount = chargeAmount;
        this.source = source;
    }

    @Override
    public void run() {
        while (true) {
            // Try charging the battery
            battery.charge(chargeAmount, source);
            
            // Simulate delay between charging intervals
            try {
                Thread.sleep(10);  // 2-second delay to simulate charging intervals
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
