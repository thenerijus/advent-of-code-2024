import java.util.Arrays;
import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/2">Day two</a>
 *
 * @author Nerijus
 */
public class Day02_1 {
    public static void main() {
        System.out.println("Reports that are safe: " + new Day02_1().getResult());
    }

    private long getResult() {
        return readReports()
                .stream()
                .filter(Report::isSafe)
                .count();
    }

    protected List<Report> readReports() {
        return Inputs.readStrings("Day02")
                .stream()
                .map(Report::new)
                .toList();
    }

    protected static class Report {
        List<Integer> levels;

        public Report(String source) {
            this(Arrays.stream(source.split(" ")).map(Integer::parseInt).toList());
        }

        public Report(List<Integer> levels) {
            this.levels = levels;
        }

        boolean isSafe() {
            var current = this.levels.get(0);
            var next = this.levels.get(1);
            if (current.equals(next)) {
                return false;
            }
            boolean isIncreasing = next > current;
            for (int i = 0; i < this.levels.size() - 1; i++) {
                current = this.levels.get(i);
                next = this.levels.get(i + 1);
                var diff = Math.abs(current - next);
                if (diff == 0 || diff > 3) {
                    return false;
                }
                if (isIncreasing && next <= current) {
                    return false;
                }
                if (!isIncreasing && next >= current) {
                    return false;
                }
            }
            return true;
        }
    }
}
