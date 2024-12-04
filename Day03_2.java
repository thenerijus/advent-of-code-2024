import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Part two of
 * <a href="https://adventofcode.com/2024/day/3">Day three</a>
 *
 * @author Nerijus
 */
public class Day03_2 extends Day03_1 {
    Pattern COMMAND_PATTERN = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)");

    public static void main() {
        System.out.println("Sum of all of the results with enabled multiplications: " + new Day03_2().getResult());
    }

    private BigDecimal getResult() {
        List<Command> all = Inputs.readStrings("Day03")
                .stream()
                .flatMap(line -> {
                    List<Command> commands = new ArrayList<>();
                    Matcher matcher = COMMAND_PATTERN.matcher(line);
                    while (matcher.find()) {
                        commands.add(new Command(matcher.group(0)));
                    }
                    return commands.stream();
                }).toList();

        BigDecimal sum = new BigDecimal(0);
        boolean add = true;
        for (Command command : all) {
            if (command.isMultiplication() && add) {
                sum = sum.add(runMultiplication(command));
            }
            if (command.isStart()) {
                add = true;
            }
            if (command.isStop()) {
                add = false;
            }
        }
        return sum;
    }

    private BigDecimal runMultiplication(Command command) {
        String[] arguments = command.source.replace("mul(", "").replace(")", "").split(",");
        return new BigDecimal(Integer.parseInt(arguments[0])).multiply(new BigDecimal(Integer.parseInt(arguments[1])));
    }

    static class Command {
        String source;

        public Command(String source) {
            this.source = source;
        }

        boolean isMultiplication() {
            return source.startsWith("mul(");
        }
        boolean isStart() {
            return source.startsWith("do(");
        }
        boolean isStop() {
            return source.startsWith("don't(");
        }
    }
}
