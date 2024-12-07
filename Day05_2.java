import java.util.List;

/**
 * Part two of
 * <a href="https://adventofcode.com/2024/day/5">Day five</a>
 *
 * @author Nerijus
 */
public class Day05_2 extends Day05_1 {
    public static void main() {
        System.out.println("Sum of middle page numbers after correctly ordering invalid updates: " + new Day05_2().getResult());
    }

    private int getResult() {
        List<Rule> rules = readRules();
        return readUpdates()
                .stream()
                .filter(u -> !u.isValid(rules))
                .map(u1 -> fix(u1, rules))
                .mapToInt(u -> u.result(rules))
                .sum();
    }

    private Update fix(Update update, List<Rule> rules) {
        while (!update.isValid(rules)) {
            rules.forEach(r -> {
                if (update.getPages().contains(r.before()) && update.getPages().contains(r.after())) {
                    if (!update.matches(r)) {
                        int beforeIndex = update.getPages().indexOf(r.before());
                        int afterIndex = update.getPages().indexOf(r.after());
                        if (beforeIndex > afterIndex) {
                            Integer item = update.getPages().get(beforeIndex);
                            update.getPages().remove(item);
                            update.getPages().add(afterIndex, item);
                        }
                    }
                }
            });
        }
        return update;
    }
}
