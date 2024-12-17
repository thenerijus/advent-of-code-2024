import common.CharMap;
import common.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/10">Day ten</a>
 *
 * @author Nerijus
 */
public class Day10_1 {
    public static void main() {
        System.out.println("Sum of the scores of all trailheads: " + new Day10_1().getResult());
    }

    private int getResult() {
        CharMap map = Inputs.readCharMap("Day10");
        List<Coordinates> trailHeads = map.findAll("0");
        List<Trail> completedTrails = new ArrayList<>();
        List<Trail> rootTrails = trailHeads.stream().map(Trail::new).toList();
        rootTrails.forEach(trail -> continueTrail(trail, map, completedTrails));
        // for each start point
        return trailHeads.stream()
                // find unique end points
                .mapToInt(th -> completedTrails
                        .stream()
                        .filter(ct -> ct.points.getFirst().equals(th))
                        .map(ct -> ct.points.getLast())
                        .collect(Collectors.toSet())
                        .size()
                ).sum();
    }

    protected void continueTrail(Trail trail, CharMap map, List<Trail> completedTrails) {
        Coordinates last = trail.points.getLast();
        int currentElevation = Integer.parseInt(map.get(last));
        if (currentElevation == 9) {
            // trail ends
            completedTrails.add(trail);
            return;
        }
        int nextElevation = currentElevation + 1;
        List<Coordinates> newDirections = new ArrayList<>(last.getAllValidAdjacent(map.getMaxX(), map.getMaxY())
                .stream()
                .filter(c -> Integer.parseInt(map.get(c)) == nextElevation)
                .toList());
        if (!newDirections.isEmpty()) {
            List<Trail> newTrails = new ArrayList<>();
            for (Coordinates newDirection : newDirections) {
                newTrails.add(trail.branch(newDirection));
            }
            newTrails.forEach(t -> continueTrail(t, map, completedTrails));
        }
    }

    protected static class Trail {
        List<Coordinates> points;

        public Trail(Coordinates start) {
            this.points = new ArrayList<>(List.of(start));
        }

        public Trail(List<Coordinates> points) {
            this.points = points;
        }

        Trail branch(Coordinates next) {
            List<Coordinates> newPoints = new ArrayList<>(points);
            newPoints.add(next);
            return new Trail(newPoints);
        }
    }
}
