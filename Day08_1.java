import common.CharMap;
import common.Coordinates;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/8">Day eight</a>
 *
 * @author Nerijus
 */
public class Day08_1 {
    public static void main() {
        System.out.println("Unique locations that contain antinode: " + new Day08_1().getResult());
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
                Coordinates antinode = createAntiNode(tower, it, 1);
                if (map.isValid(antinode)) {
                    antiNodes.add(antinode);
                }
            });
        });
        return antiNodes.size();
    }

    protected Coordinates createAntiNode(Coordinates tower, Coordinates it, int multiplier) {
        int diffX = Math.abs(it.x - tower.x) * multiplier;
        int diffY = Math.abs(it.y - tower.y) * multiplier;
        int x, y;
        if (tower.x > it.x) {
            x = it.x - diffX;
        } else if (tower.x < it.x) {
            x = it.x + diffX;
        } else {
            x = it.x;
        }
        if (tower.y > it.y) {
            y = it.y - diffY;
        } else if (tower.y < it.y) {
            y = it.y + diffY;
        } else {
            y = it.y;
        }
        return new Coordinates(x, y);
    }
}
