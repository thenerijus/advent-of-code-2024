import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/3">Day three</a>
 *
 * @author Nerijus
 */
public class Day03_1 {
    Pattern MULT_PATTERN = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

    public static void main() {
        System.out.println("Sum of all of the results of the multiplications: " + new Day03_1().getResult());
    }

    private BigDecimal getResult() {
        return sum(Inputs.readStrings("Day03")
                .stream()
                .flatMap(line -> extractMultiplications(line).stream())
                .map(Multiplication::result)
                .toList());
    }

    private BigDecimal sum(List<BigDecimal> all) {
        BigDecimal sum = new BigDecimal(0);
        for (BigDecimal bigDecimal : all) {
            sum = sum.add(bigDecimal);
        }
        return sum;
    }

    private List<Multiplication> extractMultiplications(String line) {
        List<Multiplication> all = new ArrayList<>();
        Matcher matcher = MULT_PATTERN.matcher(line);
        while (matcher.find()) {
            all.add(new Multiplication(
                    new BigDecimal(Integer.parseInt(matcher.group(1))),
                    new BigDecimal(Integer.parseInt(matcher.group(2)))));
        }
        return all;
    }

    static class Multiplication {
        BigDecimal a;
        BigDecimal b;

        public Multiplication(BigDecimal a, BigDecimal b) {
            this.a = a;
            this.b = b;
        }

        BigDecimal result() {
            return a.multiply(b);
        }
    }
}
