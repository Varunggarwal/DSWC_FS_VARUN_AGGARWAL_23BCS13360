package Day4.Q2;

import java.util.*;
import java.util.stream.Collectors;

// Abstract Base Class
abstract class MemoryEngram {
    protected String engramId;
    protected double clarityScore;
    protected boolean isCorrupted;

    public MemoryEngram(String engramId,
                        double clarityScore,
                        boolean isCorrupted) {
        this.engramId = engramId;
        this.clarityScore = clarityScore;
        this.isCorrupted = isCorrupted;
    }

    public String getEngramId() {
        return engramId;
    }

    public double getClarityScore() {
        return clarityScore;
    }

    public boolean isCorrupted() {
        return isCorrupted;
    }
}

// Standard Engram
class StandardEngram extends MemoryEngram {

    public StandardEngram(String engramId,
                          double clarityScore,
                          boolean isCorrupted) {
        super(engramId, clarityScore, isCorrupted);
    }
}

// Classified Engram
class ClassifiedEngram extends MemoryEngram {
    private int securityClearanceLevel;

    public ClassifiedEngram(String engramId,
                            double clarityScore,
                            boolean isCorrupted,
                            int securityClearanceLevel) {
        super(engramId, clarityScore, isCorrupted);
        this.securityClearanceLevel = securityClearanceLevel;
    }

    public int getSecurityClearanceLevel() {
        return securityClearanceLevel;
    }
}

// Custom Functional Interface
@FunctionalInterface
interface EngramValidator {
    boolean validate(MemoryEngram engram);
}

// Custom Functional Interface
@FunctionalInterface
interface EngramTranslator {
    String translate(MemoryEngram engram);
}

// Processing Engine
class CortexProcessor {

    public List<String> processMemories(
            List<MemoryEngram> engrams,
            EngramValidator validator,
            EngramTranslator translator) {

        return engrams.stream()
                .filter(validator::validate)
                .filter(e -> e.getClarityScore() >= 50.0)
                .map(translator::translate)
                .collect(Collectors.toList());
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        List<MemoryEngram> engrams = Arrays.asList(
                new StandardEngram("ENG001", 95.0, false),
                new ClassifiedEngram("ENG002", 88.5, false, 5),
                new ClassifiedEngram("ENG003", 75.0, false, 2),
                new StandardEngram("ENG004", 40.0, false),
                new StandardEngram("ENG005", 85.0, true)
        );

        // Validator Lambda
        EngramValidator validator = engram -> {
            if (engram.isCorrupted()) {
                return false;
            }

            if (engram instanceof ClassifiedEngram) {
                ClassifiedEngram classified =
                        (ClassifiedEngram) engram;

                return classified.getSecurityClearanceLevel() <= 3;
            }

            return true;
        };

        // Translator Lambda
        EngramTranslator translator = engram ->
                "ENGRAM-" + engram.getEngramId()
                        + " | CLARITY: "
                        + engram.getClarityScore() + "%";

        CortexProcessor processor = new CortexProcessor();

        List<String> safeMemories =
                processor.processMemories(
                        engrams,
                        validator,
                        translator);

        System.out.println("Safe Memory Records:");

        safeMemories.forEach(System.out::println);
    }
}