import common.CharMap;
import common.Coordinates;

import java.util.*;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/12">Day twelve</a>
 *
 * @author Nerijus
 */
public class Day12_1 {
    public static void main() {
        System.out.println("Price of fencing all regions: " + new Day12_1().getResult());
    }

    private int getResult() {
        List<Region> regions = new ArrayList<>();
        CharMap farm = Inputs.readCharMap("Day12");
        Set<Coordinates> processed = new HashSet<>();
        for (Coordinates c : farm.getAllCoordinates()) {
            if (processed.contains(c)) {
                continue;
            }
            Region region = getRegion(c, farm);

            // find which fences needed
            for (Coordinates regionPoint : region.points) {
                for (Coordinates adjacent : regionPoint.getAllAdjacent()) {
                    if (!farm.getSafe(adjacent).equals(region.type)) {
                        // needs a fence there
                        region.fences += 1;
                    }
                }
            }

            processed.addAll(region.points);
            regions.add(region);
        }
        return regions.stream().mapToInt(Region::getFencingPrice).sum();
    }

    Region getRegion(Coordinates start, CharMap farm) {
        Set<Coordinates> points = new HashSet<>();
        String type = farm.get(start);
        continueRegion(type, start, points, farm);
        return new Region(type, points);
    }

    void continueRegion(String type, Coordinates point, Set<Coordinates> region, CharMap farm) {
        region.add(point);
        List<Coordinates> next = point.getAllValidAdjacent(farm.getMaxX(), farm.getMaxY()).stream()
                .filter(a -> farm.get(a).equals(type))
                .filter(a -> !region.contains(a))
                .toList();
        next.forEach(c -> continueRegion(type, c, region, farm));
    }

    static class Region {
        String type;
        Set<Coordinates> points;
        int fences;

        public Region(String type, Set<Coordinates> points) {
            this.type = type;
            this.points = points;
        }

        int getFencingPrice() {
            return points.size() * fences;
        }
    }
}
