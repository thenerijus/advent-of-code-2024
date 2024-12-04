import java.util.ArrayList;

/**
 * Part two of
 * <a href="https://adventofcode.com/2024/day/2">Day two</a>
 *
 * @author Nerijus
 */
public class Day02_2 extends Day02_1 {
    public static void main() {
        System.out.println("Reports that are safe with Problem Dampener: " + new Day02_2().getResult());
    }

    private long getResult() {
        return readReports()
                .stream()
                .map(ReportWithDampener::new)
                .filter(ReportWithDampener::isSafe)
                .count();
    }

    private record ReportWithDampener(Report report) {
        boolean isSafe() {
            if (report.isSafe()) {
                return true;
            }
            // try permutations
            for (int i = 0; i < report.levels.size(); i++) {
                var newLevels = new ArrayList<>(report.levels);
                newLevels.remove(i);
                if (new Report(newLevels).isSafe()) {
                    return true;
                }
            }

            return false;
        }
    }
}
