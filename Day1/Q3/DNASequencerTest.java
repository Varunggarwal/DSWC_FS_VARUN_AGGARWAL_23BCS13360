package Day1.Q3;

class DNASequencer {

    private StringBuilder dnaSequence;

    // Constructor with initial capacity
    public DNASequencer(int expectedSize) {
        dnaSequence = new StringBuilder(expectedSize);
    }

    // Efficiently append sensor data
    public void ingestSequence(char[] sensorData) {

        for (char c : sensorData) {
            dnaSequence.append(c);
        }
    }

    // Replace first occurrence of target sequence
    public void mutateDNA(String target, String replacement) {

        int index = dnaSequence.indexOf(target);

        if (index != -1) {
            dnaSequence.replace(
                    index,
                    index + target.length(),
                    replacement
            );
        }
    }

    public String getSequence() {
        return dnaSequence.toString();
    }
}

public class DNASequencerTest {

    public static void main(String[] args) {

        // Initial capacity prevents frequent resizing
        DNASequencer sequencer = new DNASequencer(100000);

        char[] sensorData = {
                'A', 'C', 'T', 'G',
                'A', 'A', 'C', 'T',
                'G', 'G'
        };

        // Ingest DNA sequence
        sequencer.ingestSequence(sensorData);

        System.out.println("Original DNA:");
        System.out.println(sequencer.getSequence());

        // Mutate DNA
        sequencer.mutateDNA("ACT", "TTT");

        System.out.println("\nAfter Mutation:");
        System.out.println(sequencer.getSequence());
    }
}