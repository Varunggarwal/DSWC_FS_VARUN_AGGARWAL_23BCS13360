package Day1.Q2;

class PowerManager {

    // 8 sectors stored in a single byte
    private byte sectorStates = 0;

    // Turn ON a sector
    public void turnOnSector(int sectorIndex) {
        sectorStates = (byte) (sectorStates | (1 << sectorIndex));
    }

    // Turn OFF a sector
    public void turnOffSector(int sectorIndex) {
        sectorStates = (byte) (sectorStates & ~(1 << sectorIndex));
    }

    // Check if a sector is ON
    public boolean isSectorOn(int sectorIndex) {
        return (sectorStates & (1 << sectorIndex)) != 0;
    }

    // Display binary representation
    public void displayStates() {
        String binary = String.format("%8s",
                Integer.toBinaryString(sectorStates & 0xFF))
                .replace(' ', '0');

        System.out.println("Sector States: " + binary);
    }
}

public class ColonyGridPowerManager {

    public static void main(String[] args) {

        PowerManager manager = new PowerManager();

        manager.displayStates();

        manager.turnOnSector(0);
        manager.turnOnSector(3);
        manager.turnOnSector(7);

        System.out.println("After turning ON sectors 0, 3, and 7:");
        manager.displayStates();

        System.out.println("Sector 3 ON? " +
                manager.isSectorOn(3));

        System.out.println("Sector 5 ON? " +
                manager.isSectorOn(5));

        manager.turnOffSector(3);

        System.out.println("After turning OFF sector 3:");
        manager.displayStates();

        System.out.println("Sector 3 ON? " +
                manager.isSectorOn(3));
    }
}