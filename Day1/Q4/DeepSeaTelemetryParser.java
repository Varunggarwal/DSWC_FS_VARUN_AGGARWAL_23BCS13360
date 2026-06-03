package Day1.Q4;

// Checked Exception
class HardwareLockException extends Exception {

    public HardwareLockException(String message) {
        super(message);
    }
}

// Unchecked Exception
class SensorCorruptionException extends RuntimeException {

    public SensorCorruptionException(String message) {
        super(message);
    }
}

// Resource Class
class TelemetryStream implements AutoCloseable {

    public void readData() {
        System.out.println("Reading telemetry data...");
    }

    @Override
    public void close() {
        System.out.println("TelemetryStream closed.");
    }
}

public class DeepSeaTelemetryParser {

    public static void parseTelemetry(boolean fileLocked,
                                      double temperature)
            throws HardwareLockException {

        try (TelemetryStream stream = new TelemetryStream()) {

            stream.readData();

            // Checked Exception
            if (fileLocked) {
                throw new HardwareLockException(
                        "Telemetry file is locked by OS."
                );
            }

            // Unchecked Exception
            if (temperature > 100 || temperature < -10) {
                throw new SensorCorruptionException(
                        "Impossible sensor value detected: "
                                + temperature + "°C"
                );
            }

            System.out.println("Telemetry processed successfully.");
        }
    }

    public static void main(String[] args) {

        // Case 1: Normal Operation
        try {
            parseTelemetry(false, 25);
        }
        catch (HardwareLockException e) {
            System.out.println("Checked Error: "
                    + e.getMessage());
        }

        System.out.println("----------------------");

        // Case 2: Hardware Lock
        try {
            parseTelemetry(true, 25);
        }
        catch (HardwareLockException e) {
            System.out.println("Checked Error: "
                    + e.getMessage());
        }

        System.out.println("----------------------");

        // Case 3: Sensor Corruption
        try {
            parseTelemetry(false, 500);
        }
        catch (HardwareLockException e) {
            System.out.println("Checked Error: "
                    + e.getMessage());
        }
        catch (SensorCorruptionException e) {
            System.out.println("Unchecked Error: "
                    + e.getMessage());
        }
    }
}