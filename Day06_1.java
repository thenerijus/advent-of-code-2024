import common.CharMap;
import common.Coordinates;
import common.Direction;

import java.util.*;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/6">Day six</a>
 *
 * @author Nerijus
 */
public class Day06_1 {
    public static void main() {
        System.out.println("Distinct positions will the guard visit: " + new Day06_1().getResult());
    }

    private int getResult() {
        LabMap map = readLabMap();
        Set<Coordinates> visited = map.simulateGuard(true);
        map.print();
        return visited.size();
    }

    protected LabMap readLabMap() {
        return new LabMap(Inputs.readStrings("Day06"));
    }

    protected static class LabMap extends CharMap {
        Map<Coordinates, Integer> loopProtection = new HashMap<>();

        LabMap(List<String> rows) {
            super(rows);
        }

        Set<Coordinates> simulateGuard() {
            return simulateGuard(false);
        }

        Set<Coordinates> simulateGuard(boolean updateMap) {
            Set<Coordinates> visited = new HashSet<>();
            Direction currentDirection = Direction.DOWN; // inverted as this is the direction on array
            Coordinates currentPosition = find("^");

            while (true) {
                Coordinates nextPosition = currentPosition.getAdjacent(currentDirection);
                if (!nextPosition.isValid(getMaxX(), getMaxY())) {
                    // done
                    if (updateMap) {
                        set(currentPosition, "X");
                    }
                    recordVisit(visited, currentPosition);
                    return visited;
                }

                String value = get(nextPosition);
                while ("#".equals(value)) {
                    currentDirection = currentDirection.rotateLeft();// inverted
                    nextPosition = currentPosition.getAdjacent(currentDirection);
                    value = get(nextPosition);
                }
                if (updateMap) {
                    set(currentPosition, "X");
                }
                recordVisit(visited, currentPosition);
                currentPosition = nextPosition;
            }
        }

        private void recordVisit(Set<Coordinates> visited, Coordinates currentPosition) {
            visited.add(currentPosition);

            Integer count = loopProtection.get(currentPosition);
            if (count == null) {
                count = 0;
            }
            if (count > 5) {
                throw new LoopDetectedException();
            }
            loopProtection.put(currentPosition, count + 1);
        }
    }

    static protected class LoopDetectedException extends RuntimeException {}
}
