import common.Coordinates;

import java.util.List;

/**
 * Part two of
 * <a href="https://adventofcode.com/2024/day/4">Day four</a>
 *
 * @author Nerijus
 */
public class Day04_2 extends Day04_1 {
    public static void main() {
        System.out.println("X-MAS count: " + new Day04_2().getResult());
    }

    private long getResult() {
        XLetterMap map = new XLetterMap(Inputs.readStrings("Day04"));
        return map.getAllCoordinates()
                .stream()
                .map(map::getXFrom)
                .filter(this::isValidX)
                .count();
    }

    private boolean isValidX(String[] x) {
        return (x[0].equals("MAS") || new StringBuilder(x[0]).reverse().toString().equals("MAS")) &&
                (x[1].equals("MAS") || new StringBuilder(x[1]).reverse().toString().equals("MAS"));
    }

    private static class XLetterMap extends LetterMap {
        public XLetterMap(List<String> rows) {
            super(rows);
        }

        public String[] getXFrom(Coordinates start) {
            String strikeOne = getWord(start.bottomLeft(), start, start.topRight());
            String strikeTwo = getWord(start.topLeft(), start, start.bottomRight());
            return new String[] { strikeOne, strikeTwo };
        }
    }
}
