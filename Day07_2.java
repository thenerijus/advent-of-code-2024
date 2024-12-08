import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Part two of
 * <a href="https://adventofcode.com/2024/day/7">Day seven</a>
 *
 * @author Nerijus
 */
public class Day07_2 extends Day07_1 {
    public static void main() {
        System.out.println("Total calibration result: " + new Day07_2().getResult());
    }

    private String getResult() {
        List<Function<BigDecimal[], BigDecimal>> operations = new ArrayList<>(baseOperations);
        operations.add((BigDecimal[] args) -> {
            String left = args[0].toBigInteger().toString();
            String right = args[1].toBigInteger().toString();
            return new BigDecimal(left + right);
        });
        return new CalibrationResultCalculator(readCalibrations(), operations).getResult();
    }
}
