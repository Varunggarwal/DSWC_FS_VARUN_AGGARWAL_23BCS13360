package Day1.Q1;

abstract class SpaceVessel {

    protected short shipId;
    protected boolean operationalStatus;
    protected char fleetClassification;

    public SpaceVessel(short shipId,
                       boolean operationalStatus,
                       char fleetClassification) {

        this.shipId = shipId;
        this.operationalStatus = operationalStatus;
        this.fleetClassification = fleetClassification;
    }

    public short getShipId() {
        return shipId;
    }

    public boolean isOperational() {
        return operationalStatus;
    }

    public char getFleetClassification() {
        return fleetClassification;
    }
}

class MiningShip extends SpaceVessel {

    private float[][] cargoHold;

    public MiningShip(short shipId,
                      boolean operationalStatus,
                      char fleetClassification,
                      float[][] cargoHold) {

        super(shipId, operationalStatus, fleetClassification);
        this.cargoHold = cargoHold;
    }

    public float calculateTotalOreWeight() {

        float totalWeight = 0.0f;

        for (int i = 0; i < cargoHold.length; i++) {
            for (int j = 0; j < cargoHold[i].length; j++) {
                totalWeight += cargoHold[i][j];
            }
        }

        return totalWeight;
    }

    public float findHeaviestContainer() {

        float heaviest = Float.MIN_VALUE;

        for (int i = 0; i < cargoHold.length; i++) {
            for (int j = 0; j < cargoHold[i].length; j++) {

                if (cargoHold[i][j] > heaviest) {
                    heaviest = cargoHold[i][j];
                }
            }
        }

        return heaviest;
    }
}

public class InterstellarMiningFleetManager {

    public static void main(String[] args) {

        float[][] cargo1 = {
                {1200.5f, 3400.8f, 2200.2f},
                {4500.0f, 3800.6f},
                {1500.4f, 6000.9f, 2900.3f}
        };

        float[][] cargo2 = {
                {5000.0f, 4200.7f},
                {3100.5f, 2100.8f, 7000.4f}
        };

        MiningShip ship1 = new MiningShip(
                (short) 1001,
                true,
                'A',
                cargo1
        );

        MiningShip ship2 = new MiningShip(
                (short) 1002,
                false,
                'B',
                cargo2
        );

        SpaceVessel[] fleet = new SpaceVessel[2];
        fleet[0] = ship1;
        fleet[1] = ship2;

        for (SpaceVessel vessel : fleet) {

            if (vessel instanceof MiningShip) {

                MiningShip ship = (MiningShip) vessel;

                System.out.println("Ship ID: " + ship.getShipId());
                System.out.println("Operational: " + ship.isOperational());
                System.out.println("Class: " + ship.getFleetClassification());

                System.out.println(
                        "Total Ore Weight: "
                                + ship.calculateTotalOreWeight()
                                + " kg"
                );

                System.out.println(
                        "Heaviest Container: "
                                + ship.findHeaviestContainer()
                                + " kg"
                );
            }
        }
    }
}
