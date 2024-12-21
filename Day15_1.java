import common.CharMap;
import common.Coordinates;
import common.Direction;

import java.util.*;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/15">Day fifteen</a>
 *
 * @author Nerijus
 */
public class Day15_1 {
    public static void main() {
        System.out.println("Sum of all boxes' GPS coordinates: " + new Day15_1().getResult());
    }

    private long getResult() {
        List<String> rows = Inputs.readStrings("Day15");
        int separatorIndex = rows.indexOf("");
        CharMap map = new CharMap(rows.subList(0, separatorIndex));
        List<Direction> directions = rows.subList(separatorIndex + 1, rows.size())
                .stream()
                .flatMap(r -> Arrays.stream(r.split("")))
                .map(arrow -> {
                    Direction direction = Direction.from(arrow);
                    // invert up and down due to how map is stored on array
                    if (Direction.UP == direction) {
                        return Direction.DOWN;
                    }
                    if (Direction.DOWN == direction) {
                        return Direction.UP;
                    }
                    return direction;
                })
                .toList();

        directions.forEach(d -> move(map, d));

        return map.findAll("O").stream().mapToLong(c -> c.y * 100L + c.x).sum();
    }

    private void move(CharMap map, Direction direction) {
        Coordinates robotLocation = map.find("@");
        Coordinates nextLocation = robotLocation.getAdjacent(direction);
        String next = map.get(nextLocation);
        if (next.equals(".")) {
            // just an empty space, move to it
            map.set(robotLocation, ".");
            map.set(nextLocation, "@");
        } else if ("O".equals(next)) {
            // might need to move some boxes
            List<MapLocation> line = getLineToWall(nextLocation, direction, map);
            if (line.stream().anyMatch(l -> ".".equals(l.value))) {
                // there is some space
                // robot moves
                map.set(line.getFirst().location, "@");
                map.set(robotLocation, ".");
                // pushes boxes to the first space
                for (MapLocation mapLocation : line) {
                    if (mapLocation.value.equals(".")) {
                        map.set(mapLocation.location, "O");
                        break;
                    }
                }
            }
        }
    }

    List<MapLocation> getLineToWall(Coordinates start, Direction direction, CharMap map) {
        List<MapLocation> line = new ArrayList<>();
        line.add(new MapLocation(start, map.get(start)));
        Coordinates next = start.getAdjacent(direction);
        while (map.isValid(next)) {
            String value = map.get(next);
            line.add(new MapLocation(next, value));
            if ("#".equals(value)) {
                // wall
                break;
            }
            next = next.getAdjacent(direction);
        }
        return line;
    }

    record MapLocation(Coordinates location, String value) {
    }
}