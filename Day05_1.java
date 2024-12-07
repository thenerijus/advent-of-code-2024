import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/5">Day five</a>
 *
 * @author Nerijus
 */
public class Day05_1 {

    public static void main() {
        System.out.println("Sum of middle page number from correctly-ordered updates: " + new Day05_1().getResult());
    }

    private int getResult() {
        return readUpdates()
                .stream()
                .mapToInt(u -> u.result(readRules()))
                .sum();
    }

    protected List<Rule> readRules() {
        List<String> lines = Inputs.readStrings("Day05");
        int split = lines.indexOf("");
        return lines.subList(0, split).stream().map(ruleLine -> {
            String[] parts = ruleLine.split("\\|");
            return new Rule(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }).toList();
    }

    protected List<Update> readUpdates() {
        List<String> lines = Inputs.readStrings("Day05");
        int split = lines.indexOf("");
        return lines.subList(split + 1, lines.size()).stream().map(Update::new).toList();
    }

    protected record Rule(int before, int after) {}

    protected static class Update {
        private final List<Integer> pages;

        Update(String source) {
            this(Arrays.stream(source.split(",")).map(Integer::parseInt).toList());
        }

        Update(List<Integer> pages) {
            this.pages = new ArrayList<>(pages);
        }

        boolean isValid(List<Rule> rules) {
            return rules.stream().allMatch(this::matches);
        }

        boolean matches(Rule rule) {
            if (!pages.contains(rule.before) || !pages.contains(rule.after)) {
                // not applicable
                return true;
            }

            int indexBefore = pages.indexOf(rule.before);
            int indexAfter = pages.indexOf(rule.after);

            return indexBefore < indexAfter;
        }

        int result(List<Rule> rules) {
            if (!isValid(rules)) {
                return 0;
            }
            return pages.get((pages.size() - 1) / 2);
        }

        public List<Integer> getPages() {
            return pages;
        }
    }
}
