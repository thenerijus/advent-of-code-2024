import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/1">Day one</a>
 *
 * @author Nerijus
 */
public class Day01_1 {
    public static void main() {
        System.out.println("Total distance between lists: " + new Day01_1().getResult());
    }

    private int getResult() {
        Day1Input input = readInput();

        // get all distances
        int distance = 0;
        for (int i = 0; i < input.left.size(); i++) {
            distance += Math.abs(input.left.get(i) - input.right.get(i));
        }

        return distance;
    }

    protected Day1Input readInput() {
        // read lists
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        Inputs.readStrings("Day01").forEach(line -> {
            String[] parts = line.split("\\s+");
            left.add(Integer.parseInt(parts[0]));
            right.add(Integer.parseInt(parts[1]));
        });

        // sort
        Collections.sort(left);
        Collections.sort(right);

        return new Day1Input(left, right);
    }

    protected record Day1Input(List<Integer> left, List<Integer> right){}
}
