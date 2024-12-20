package common;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

    private CharMap(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.map = new String[maxY][maxX];
    }

    public static CharMap empty(int maxX, int maxY) {
        CharMap empty = new CharMap(maxX, maxY);
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                empty.map[y][x] = ".";
            }
        }
        return empty;
    }

    public void readMap(List<String> rows) {
        for (int y = 0; y < rows.size(); y++) {
            String[] letters = rows.get(y).split("");
            for (int x = 0; x < letters.length; x++) {
                map[y][x] = letters[x];
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

    public Coordinates find(String value) {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (map[y][x].equals(value)) {
                    return new Coordinates(x, y);
                }
            }
        }
        throw new IllegalStateException("No such value");
    }

    public List<Coordinates> findAll(String value) {
        List<Coordinates> found = new ArrayList<>();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (map[y][x].equals(value)) {
                    found.add(new Coordinates(x, y));
                }
            }
        }
        return found;
    }

    public List<Coordinates> findAll(Function<String, Boolean> matcher) {
        List<Coordinates> found = new ArrayList<>();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (matcher.apply(map[y][x])) {
                    found.add(new Coordinates(x, y));
                }
            }
        }
        return found;
    }

    public String get(Coordinates c) {
        return map[c.y][c.x];
    }

    public String getSafe(Coordinates c) {
        if (!isValid(c)) {
            return "";
        }
        return map[c.y][c.x];
    }

    public String get(int x, int y) {
        return map[y][x];
    }

    public void set(Coordinates c, String newValue) {
        map[c.y][c.x] = newValue;
    }

    public int count(String value) {
        int count = 0;
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (map[y][x].equals(value)) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public boolean isValid(Coordinates c) {
        return c.isValid(getMaxX(), getMaxY());
    }

    public void print() {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                System.out.print(map[y][x]);
            }
            System.out.print("\n");
        }
    }

    public void print(BufferedWriter bw) throws IOException {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                bw.write(map[y][x]);
            }
            bw.write("\n");
        }
    }
}