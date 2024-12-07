import common.Coordinates;

import java.util.HashSet;
import java.util.Set;

/**
 * Part two of
 * <a href="https://adventofcode.com/2024/day/6">Day six</a>
 *
 * @author Nerijus
 */
public class Day06_2 extends Day06_1 {
    public static void main() {
        System.out.println("Possible obstruction positions: " + new Day06_2().getResult());
    }

    private int getResult() {
        LabMap map = readLabMap();
        Coordinates startPosition = map.find("^");
        Set<Coordinates> possibleObstruction = map.simulateGuard();
        possibleObstruction.remove(startPosition);

        int count = 0;
        for (Coordinates coordinates : possibleObstruction) {
            LabMap updatedMap = readLabMap();

            // set obstruction
            updatedMap.set(coordinates, "#");
            // simulate
            try {
                updatedMap.simulateGuard();
            } catch (LoopDetectedException e) {
                // ends up in a loop
                count += 1;
            }
        }
        return count;
    }
}
