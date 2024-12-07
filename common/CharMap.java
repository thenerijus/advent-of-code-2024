package common;

import java.util.ArrayList;
import java.util.List;

public class CharMap {
    private final int maxX;
    private final int maxY;
    private final String[][] map;

    public CharMap(List<String> rows) {
        this.maxX = rows.getFirst().length();
        this.maxY = rows.size();
        this.map = new String[maxY][maxX];
        readMap(rows);
    }

    public void readMap(List<String> rows) {
        for (int y = 0; y < rows.size(); y++) {
            String[] letters = rows.get(y).split("");
            for (int x = 0; x < letters.length; x++) {
                map[x][y] = letters[x];
            }
        }
    }

    public List<Coordinates> getAllCoordinates() {
        List<Coordinates> all = new ArrayList<>();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                all.add(new Coordinates(x, y));
            }
        }
        return all;
    }

    public String get(int x, int y) {
        return map[y][x];
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}