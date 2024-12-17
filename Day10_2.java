import common.CharMap;
import common.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part two of
 * <a href="https://adventofcode.com/2024/day/10">Day ten</a>
 *
 * @author Nerijus
 */
public class Day10_2 extends Day10_1 {
    public static void main() {
        System.out.println("Sum of the ratings of all trailheads: " + new Day10_2().getResult());
    }

    private long getResult() {
        CharMap map = Inputs.readCharMap("Day10");
        List<Coordinates> trailHeads = map.findAll("0");
        List<Trail> completedTrails = new ArrayList<>();

        // get completed trails
        trailHeads
            .stream()
            .map(Trail::new)
            .forEach(trail -> continueTrail(trail, map, completedTrails));

        return trailHeads
                .stream()
                .mapToLong(th -> getRating(th, completedTrails))
                .sum();
    }

    private static long getRating(Coordinates trailHead, List<Trail> completedTrails) {
        return completedTrails
                    .stream()
                    .filter(c -> c.points.getFirst().equals(trailHead))
                    .map(tr -> tr.points.getLast())
                    .collect(Collectors.toSet())
                    .stream()
                    .mapToLong(trailEnd -> completedTrails
                        .stream()
                        .filter(c -> c.points.getFirst().equals(trailHead) && c.points.getLast().equals(trailEnd))
                        .count())
                .sum();
    }
}
