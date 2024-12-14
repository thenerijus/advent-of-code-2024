import common.CharMap;
import common.Coordinates;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Part two of
 * <a href="https://adventofcode.com/2024/day/8">Day eight</a>
 *
 * @author Nerijus
 */
public class Day08_2 extends Day08_1 {
    public static void main() {
        System.out.println("Unique locations that contain antinode: " + new Day08_2().getResult());
    }

    private int getResult() {
        Set<Coordinates> antiNodes = new HashSet<>();
        CharMap map = new CharMap(Inputs.readStrings("Day08"));
        List<Coordinates> towers = map.findAll((val) -> !val.equals("."));
        towers.forEach(tower -> {
            String towerSymbol = map.get(tower);
            List<Coordinates> otherTowers = map.findAll(towerSymbol);
            otherTowers.remove(tower); // remove self
            List<Coordinates> inlineTowers = otherTowers.stream().toList();
            inlineTowers.forEach(it -> {
                int multiplier = 1;
                antiNodes.add(tower);
                Coordinates antinode = createAntiNode(tower, it, multiplier);
                while (map.isValid(antinode)) {
                    antiNodes.add(antinode);
                    multiplier = multiplier + 1;
                    antinode = createAntiNode(tower, it, multiplier);
                }
            });
        });
        return antiNodes.size();
    }
}
