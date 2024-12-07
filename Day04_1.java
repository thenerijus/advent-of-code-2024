import common.CharMap;
import common.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/4">Day four</a>
 *
 * @author Nerijus
 */
public class Day04_1 {

    public static void main() {
        System.out.println("XMAS count: " + new Day04_1().getResult());
    }

    private long getResult() {
        LetterMap map = new LetterMap(Inputs.readStrings("Day04"));
        return map
                .getAllCoordinates()
                .stream()
                .flatMap(c -> map.getWordsFrom(c).stream())
                .filter("XMAS"::equals)
                .count();
    }

    protected static class LetterMap extends CharMap {
        public LetterMap(List<String> rows) {
            super(rows);
        }

        public List<String> getWordsFrom(Coordinates start) {
            List<String> words = new ArrayList<>();
            // right
            words.add(getWord(start, start.right(), start.right().right(), start.right().right().right()));
            // bottom right
            words.add(getWord(start, start.bottomRight(), start.bottomRight().bottomRight(), start.bottomRight().bottomRight().bottomRight()));
            // bottom
            words.add(getWord(start, start.bottom(), start.bottom().bottom(), start.bottom().bottom().bottom()));
            // bottom left
            words.add(getWord(start, start.bottomLeft(), start.bottomLeft().bottomLeft(), start.bottomLeft().bottomLeft().bottomLeft()));
            // left
            words.add(getWord(start, start.left(), start.left().left(), start.left().left().left()));
            // top left
            words.add(getWord(start, start.topLeft(), start.topLeft().topLeft(), start.topLeft().topLeft().topLeft()));
            // top
            words.add(getWord(start, start.top(), start.top().top(), start.top().top().top()));
            // top right
            words.add(getWord(start, start.topRight(), start.topRight().topRight(), start.topRight().topRight().topRight()));

            return words;
        }

        protected String getWord(Coordinates... coordinates) {
            StringBuilder word = new StringBuilder();
            for (Coordinates c : coordinates) {
                if (c.isValid(getMaxX(), getMaxY())) {
                    word.append(get(c.x, c.y));
                }
            }
            return word.toString();
        }
    }
}
