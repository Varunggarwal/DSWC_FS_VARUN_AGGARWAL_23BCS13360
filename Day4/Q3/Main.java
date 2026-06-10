package Day4.Q3;

import java.util.*;

// Abstract Base Class
abstract class EngineLog {
    protected String timestamp;
    protected double coreTemperature;
    protected boolean isAnomaly;

    public EngineLog(String timestamp,
                     double coreTemperature,
                     boolean isAnomaly) {
        this.timestamp = timestamp;
        this.coreTemperature = coreTemperature;
        this.isAnomaly = isAnomaly;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getCoreTemperature() {
        return coreTemperature;
    }

    public boolean isAnomaly() {
        return isAnomaly;
    }
}

// Nominal Log
class NominalLog extends EngineLog {

    public NominalLog(String timestamp,
                      double coreTemperature,
                      boolean isAnomaly) {
        super(timestamp, coreTemperature, isAnomaly);
    }
}

// Critical Log
class CriticalLog extends EngineLog {
    private String errorCode;

    public CriticalLog(String timestamp,
                       double coreTemperature,
                       boolean isAnomaly,
                       String errorCode) {
        super(timestamp, coreTemperature, isAnomaly);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

// Custom Functional Interface
@FunctionalInterface
interface LogAuditor {
    boolean audit(EngineLog log);
}

// Custom Functional Interface
@FunctionalInterface
interface HeatExtractor {
    double extract(EngineLog log);
}

// Processing Engine
class TelemetryProcessor {

    public double getPeakCriticalTemp(
            List<EngineLog> logs,
            LogAuditor auditor,
            HeatExtractor extractor) {

        return logs.stream()
                .filter(auditor::audit)
                .mapToDouble(extractor::extract)
                .max()
                .orElse(0.0);
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        List<EngineLog> logs = Arrays.asList(

                new NominalLog(
                        "2026-08-01T10:00",
                        450.5,
                        false),

                new CriticalLog(
                        "2026-08-01T10:01",
                        890.2,
                        false,
                        "OVERHEAT"),

                new NominalLog(
                        "2026-08-01T10:02",
                        500.0,
                        true),

                new CriticalLog(
                        "2026-08-01T10:03",
                        920.8,
                        false,
                        "FUEL_LEAK"),

                new CriticalLog(
                        "2026-08-01T10:04",
                        980.3,
                        true,
                        "OVERHEAT")
        );

        // Auditor Lambda
        LogAuditor auditor = log -> {

            if (log.isAnomaly()) {
                return true;
            }

            if (log instanceof CriticalLog) {
                CriticalLog critical =
                        (CriticalLog) log;

                return "OVERHEAT".equals(
                        critical.getErrorCode());
            }

            return false;
        };

        // Extractor Lambda
        HeatExtractor extractor =
                EngineLog::getCoreTemperature;

        TelemetryProcessor processor =
                new TelemetryProcessor();

        double peakTemperature =
                processor.getPeakCriticalTemp(
                        logs,
                        auditor,
                        extractor);

        System.out.println(
                "Peak Critical Temperature: "
                        + peakTemperature);
    }
}