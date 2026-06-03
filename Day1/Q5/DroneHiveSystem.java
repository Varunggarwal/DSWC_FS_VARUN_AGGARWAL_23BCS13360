package Day1.Q5;

import java.util.concurrent.atomic.AtomicInteger;

class DroneHive {

    // Thread-safe counter
    private AtomicInteger totalDronesReturned = new AtomicInteger(0);

    // Visibility guarantee across all threads
    private volatile boolean emergencyAbort = false;

    // Drone lands and increments counter
    public void droneReturned() {
        totalDronesReturned.incrementAndGet();
    }

    // Radar detects storm
    public void triggerEmergencyAbort() {
        emergencyAbort = true;
        System.out.println("\n*** STORM DETECTED! EMERGENCY ABORT ACTIVATED ***\n");
    }

    public boolean isEmergencyAbort() {
        return emergencyAbort;
    }

    public int getTotalDronesReturned() {
        return totalDronesReturned.get();
    }
}

class Drone extends Thread {

    private DroneHive hive;
    private int droneId;

    public Drone(int droneId, DroneHive hive) {
        this.droneId = droneId;
        this.hive = hive;
    }

    @Override
    public void run() {

        try {

            // Simulate flight time
            Thread.sleep((long) (Math.random() * 3000));

            if (hive.isEmergencyAbort()) {
                System.out.println("Drone " + droneId
                        + " rerouting due to storm.");
                return;
            }

            hive.droneReturned();

            System.out.println("Drone " + droneId
                    + " landed successfully.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class DroneHiveSystem {

    public static void main(String[] args) {

        DroneHive hive = new DroneHive();

        int numberOfDrones = 10;

        Drone[] drones = new Drone[numberOfDrones];

        // Launch drones
        for (int i = 0; i < numberOfDrones; i++) {
            drones[i] = new Drone(i + 1, hive);
            drones[i].start();
        }

        try {

            // Wait before storm detection
            Thread.sleep(1500);

            hive.triggerEmergencyAbort();

            // Wait for all drones
            for (Drone drone : drones) {
                drone.join();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nTotal Drones Returned: "
                + hive.getTotalDronesReturned());

        System.out.println("Emergency Abort Status: "
                + hive.isEmergencyAbort());
    }
}