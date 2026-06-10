package Day4.Q4;

import java.util.*;
import java.util.stream.Collectors;

// Abstract Base Class
abstract class TemporalEntity {
    protected String entityName;
    protected int originYear;

    public TemporalEntity(String entityName, int originYear) {
        this.entityName = entityName;
        this.originYear = originYear;
    }

    public String getEntityName() {
        return entityName;
    }

    public int getOriginYear() {
        return originYear;
    }
}

// Human Entity
class HumanEntity extends TemporalEntity {

    public HumanEntity(String entityName, int originYear) {
        super(entityName, originYear);
    }
}

// Artifact Entity
class ArtifactEntity extends TemporalEntity {
    private boolean isRadioactive;

    public ArtifactEntity(String entityName,
                          int originYear,
                          boolean isRadioactive) {
        super(entityName, originYear);
        this.isRadioactive = isRadioactive;
    }

    public boolean isRadioactive() {
        return isRadioactive;
    }
}

// Historical Event
class HistoricalEvent {
    private List<TemporalEntity> entities;
    private int eventYear;

    public HistoricalEvent(List<TemporalEntity> entities,
                           int eventYear) {
        this.entities = entities;
        this.eventYear = eventYear;
    }

    public List<TemporalEntity> getEntities() {
        return entities;
    }

    public int getEventYear() {
        return eventYear;
    }
}

// Custom Functional Interface
@FunctionalInterface
interface ParadoxChecker {
    boolean check(TemporalEntity entity, int eventYear);
}

// Custom Functional Interface
@FunctionalInterface
interface ThreatMapper {
    String map(TemporalEntity entity);
}

// Helper Class to Preserve Context
class EventEntityPair {
    private TemporalEntity entity;
    private int eventYear;

    public EventEntityPair(TemporalEntity entity, int eventYear) {
        this.entity = entity;
        this.eventYear = eventYear;
    }

    public TemporalEntity getEntity() {
        return entity;
    }

    public int getEventYear() {
        return eventYear;
    }
}

// Processing Engine
class ParadoxAnalyzer {

    public List<String> detectParadoxes(
            List<HistoricalEvent> timeline,
            ParadoxChecker checker,
            ThreatMapper mapper) {

        return timeline.stream()

                // FlatMap while preserving event year
                .flatMap(event ->
                        event.getEntities()
                                .stream()
                                .map(entity ->
                                        new EventEntityPair(
                                                entity,
                                                event.getEventYear()
                                        )
                                )
                )

                // Filter paradoxes
                .filter(pair ->
                        checker.check(
                                pair.getEntity(),
                                pair.getEventYear()
                        )
                )

                // Map to threat report
                .map(pair ->
                        mapper.map(pair.getEntity())
                )

                .collect(Collectors.toList());
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        HistoricalEvent event1 =
                new HistoricalEvent(
                        Arrays.asList(
                                new HumanEntity(
                                        "John Traveler",
                                        2050),

                                new ArtifactEntity(
                                        "Ancient Relic",
                                        1500,
                                        false)
                        ),
                        2025
                );

        HistoricalEvent event2 =
                new HistoricalEvent(
                        Arrays.asList(
                                new HumanEntity(
                                        "Sarah",
                                        1980),

                                new ArtifactEntity(
                                        "Quantum Device",
                                        2200,
                                        true)
                        ),
                        2000
                );

        List<HistoricalEvent> timeline =
                Arrays.asList(event1, event2);

        // Checker Lambda
        ParadoxChecker checker =
                (entity, eventYear) ->
                        entity.getOriginYear() > eventYear;

        // Mapper Lambda
        ThreatMapper mapper =
                entity ->
                        entity.getEntityName()
                                + " detected out of time!";

        ParadoxAnalyzer analyzer =
                new ParadoxAnalyzer();

        List<String> paradoxes =
                analyzer.detectParadoxes(
                        timeline,
                        checker,
                        mapper);

        System.out.println("Temporal Paradox Report:");

        paradoxes.forEach(System.out::println);
    }
}