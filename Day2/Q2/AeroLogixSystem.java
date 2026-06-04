package Day2.Q2;


interface Airborne {

    void flyToDestination();

    default void requestAirTrafficClearance() {
        System.out.println("Air Traffic Clearance Granted. Proceed to destination.");
    }
}

interface GroundBased {
    void navigateSidewalks();
}

abstract class DeliveryDrone {
    protected String droneId;

    public DeliveryDrone(String droneId) {
        this.droneId = droneId;
    }

    public abstract void deliverPackage();
}

class Quadcopter extends DeliveryDrone implements Airborne {

    public Quadcopter(String droneId) {
        super(droneId);
    }

    @Override
    public void flyToDestination() {
        System.out.println("Quadcopter " + droneId + " flying to destination.");
    }

    @Override
    public void deliverPackage() {
        requestAirTrafficClearance();
        flyToDestination();
        System.out.println("Quadcopter " + droneId + " delivered the package.");
    }
}

class CityRover extends DeliveryDrone implements GroundBased {

    public CityRover(String droneId) {
        super(droneId);
    }

    @Override
    public void navigateSidewalks() {
        System.out.println("CityRover " + droneId + " navigating sidewalks.");
    }

    @Override
    public void deliverPackage() {
        navigateSidewalks();
        System.out.println("CityRover " + droneId + " delivered the package.");
    }
}

class HybridVTOL extends DeliveryDrone
        implements Airborne, GroundBased {

    public HybridVTOL(String droneId) {
        super(droneId);
    }

    @Override
    public void flyToDestination() {
        System.out.println("HybridVTOL " + droneId + " flying to destination.");
    }

    @Override
    public void navigateSidewalks() {
        System.out.println("HybridVTOL " + droneId + " navigating on ground.");
    }

    @Override
    public void deliverPackage() {
        requestAirTrafficClearance();
        flyToDestination();
        navigateSidewalks();
        System.out.println("HybridVTOL " + droneId + " delivered the package.");
    }
}

public class AeroLogixSystem {

    public static void main(String[] args) {

        DeliveryDrone d1 = new Quadcopter("QD-101");
        DeliveryDrone d2 = new CityRover("CR-202");
        DeliveryDrone d3 = new HybridVTOL("HV-303");

        System.out.println("Quadcopter");
        d1.deliverPackage();

        System.out.println("\nCity Rover");
        d2.deliverPackage();

        System.out.println("\nHybrid VTOL");
        d3.deliverPackage();
    }
}