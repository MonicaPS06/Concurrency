package EnergyManagementSystemProject;

public class ControlSystem {
    private Battery battery;
    private EnergySourceSimulator solar;
    private EnergySourceSimulator wind;
    private EnergySourceSimulator hydro;
    private BatteryUsageSimulator highPowerDevice;
    private BatteryUsageSimulator lowPowerDevice;
    private BatteryUsageSimulator variablePowerDevice;
    private boolean chargingStopped = false; // Track if charging is already stopped
    private boolean consumptionReduced = false; // Track if consumption is already reduced

    public ControlSystem(Battery battery, 
                         EnergySourceSimulator solar, 
                         EnergySourceSimulator wind, 
                         EnergySourceSimulator hydro,
                         BatteryUsageSimulator highPowerDevice, 
                         BatteryUsageSimulator lowPowerDevice, 
                         BatteryUsageSimulator variablePowerDevice) {
        this.battery = battery;
        this.solar = solar;
        this.wind = wind;
        this.hydro = hydro;
        this.highPowerDevice = highPowerDevice;
        this.lowPowerDevice = lowPowerDevice;
        this.variablePowerDevice = variablePowerDevice;
    }

    public void monitorAndControl() {
        long startTime = System.currentTimeMillis();
        long duration = 3000; // Monitor for 3 seconds

        while (System.currentTimeMillis() - startTime < duration) {
            double currentCharge = battery.getCurrentCharge();
            double capacity = battery.getCapacity();
            double upperThreshold = capacity * 0.75;
            double lowerThreshold = capacity * 0.25;

         // Check if the battery charge is zero
            if (currentCharge == 0) {
                System.out.println("[CONTROL MESSAGE] Battery charge is zero. Stopping all consumption...\n");
                highPowerDevice.stopUsage();
                lowPowerDevice.stopUsage();
                variablePowerDevice.stopUsage();
             
                // Attempt to start energy sources if they are not alive
                if (!solar.isAlive() && !wind.isAlive() && !hydro.isAlive()) {
                    System.out.println("[CONTROL MESSAGE] Restarting energy sources to charge the battery...\n");
                    solar = new EnergySourceSimulator(battery, 20, "Solar");
                    wind = new EnergySourceSimulator(battery, 30, "Wind");
                    hydro = new EnergySourceSimulator(battery, 25, "Hydro");
                    
                    solar.start();
                    wind.start();
                    hydro.start();
                }
                break; 
            }
            
            // Stop charging if the battery is full
            else if (currentCharge == capacity && !chargingStopped) {
                System.out.println("[CONTROL MESSAGE] Battery nearly full. Stopping charging...\n");
                solar.stopCharging();
                wind.stopCharging();
                hydro.stopCharging();
                chargingStopped = true; // Mark that charging has been stopped
                break; 
            }



            // Resume charging if the battery falls below a certain level
            else if (currentCharge >= lowerThreshold && currentCharge < upperThreshold && chargingStopped) {
                System.out.println("[CONTROL MESSAGE] Battery charge dropped. Resuming charging...\n");
                if (!solar.isAlive() && !wind.isAlive() && !hydro.isAlive()) {
                    // Create new instances to restart charging only if none are alive
                    solar = new EnergySourceSimulator(battery, 20, "Solar");
                    wind = new EnergySourceSimulator(battery, 30, "Wind");
                    hydro = new EnergySourceSimulator(battery, 25, "Hydro");
                    
                    solar.start();
                    wind.start();
                    hydro.start();
                    chargingStopped = false; // Mark that charging has been resumed
                }
                break; 
            }

            // Reduce consumption if the battery level is too low
            else if (currentCharge > 0 && currentCharge < lowerThreshold && !consumptionReduced) {
            	System.out.println("[CONTROL MESSAGE] Battery charge low. Reducing device consumption...\n");
                highPowerDevice.stopUsage(); // Stop high power device gracefully
                consumptionReduced = true; // Mark that consumption has been reduced
                break; 
            }

            // Allow devices to resume if the charge improves
            else if (currentCharge < capacity && currentCharge >= lowerThreshold && consumptionReduced) {
                System.out.println("[CONTROL MESSAGE] Battery charge stabilized. Resuming device consumption...\n");
                if (!highPowerDevice.isAlive()) {
                	highPowerDevice = new BatteryUsageSimulator(battery, 15, "HighPowerDevice", false);
                    highPowerDevice.start(); // Restart high-power device if not alive
                }
                consumptionReduced = false; // Reset the consumption reduced flag
                break; 
            }
            
            else {
            	System.out.printf("[CONTROL MESSAGE] Current battery charge: %.1f units. No immediate action required.\n\n", currentCharge);
            	break;
            }

            
        }

       // stopAll(); // Ensure all threads are stopped after the duration
    
    }


    public void stopAll() {
        solar.stopCharging();
        wind.stopCharging();
        hydro.stopCharging();
        highPowerDevice.stopUsage();
        lowPowerDevice.stopUsage();
        variablePowerDevice.stopUsage();
        System.out.println("[CONTROL MESSAGE] All threads stopped due to control conditions.\n");
        System.out.println(""); // Print an empty line for clarity
    }
}
