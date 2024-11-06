package EnergyManagementSystemProject;

public class Battery {
    private double capacity;
    private double currentCharge;
    private ControlSystem controlSystem;
    private boolean warningDisplayed = false; // Flag to manage warning display

    public Battery(double capacity, ControlSystem constrolSystem) {
        this.capacity = capacity;
        this.currentCharge = 0;
        this.controlSystem = controlSystem;
    }

    public synchronized void charge(double amount, String source, double elapsedTime) {
    	//System.out.printf("[DEBUG] Charging from %s: Attempting to charge %.1f units. Current charge: %.1f\n", source, amount, currentCharge);
    	
        if (currentCharge < capacity) {
            double potentialCharge = currentCharge + amount;

            if (potentialCharge > capacity) {
                currentCharge = capacity; // Prevent over-charging
                System.out.printf("[INFO] %s: Charged to full capacity (%.1f units).\n\n", source, currentCharge);
                warningDisplayed = false;
            } else {
                currentCharge += amount; // Increase current charge
                System.out.printf("[INFO] %s: Charged %.1f units. Current charge: %.1f units.\n\n", source, amount, currentCharge);
            }
            controlSystem.monitorAndControl(); // Call the monitor method whenever charge changes
        } else {
        	if (!warningDisplayed) { // Check if warning has already been displayed
                System.out.printf("[WARNING] %s: Allready at full capacity (%.1f units).\n\n", source, currentCharge);
                warningDisplayed = true; // Set flag to prevent further warnings
            } 
        	return;
        }
    }

    public synchronized double getCurrentCharge() {
        return currentCharge;
    }

    public synchronized double getCapacity() {
        return capacity;
    }
    
    public void setControlSystem(ControlSystem controlSystem) {
        this.controlSystem = controlSystem;
    }
}
