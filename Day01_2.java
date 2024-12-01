import java.util.List;

/**
 * Part two of
 * <a href="https://adventofcode.com/2024/day/1">Day one</a>
 *
 * @author Nerijus
 */
public class Day01_2 extends Day01_1 {
    public static void main() {
        System.out.println("Similarity score: " + new Day01_2().getResult());
    }

    private long getResult() {
        Day1Input input = readInput();
        long similarityScore = 0;
        for (int i = 0; i < input.left().size(); i++) {
            Integer value = input.left().get(i);
            similarityScore += value * countOccurrences(value, input.right());
        }
        return similarityScore;
    }

    private long countOccurrences(Integer what, List<Integer> where) {
        return where.stream().filter(i -> i.equals(what)).count();
    }
}
