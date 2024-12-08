import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/7">Day seven</a>
 *
 * @author Nerijus
 */
public class Day07_1 {
    protected List<Function<BigDecimal[], BigDecimal>> baseOperations = List.of(
            (BigDecimal[] args) -> args[0].multiply(args[1]),
            (BigDecimal[] args) -> args[0].add(args[1])
    );

    public static void main() {
        System.out.println("Total calibration result: " + new Day07_1().getResult());
    }

    private String getResult() {
        List<Calibration> calibrations = readCalibrations();
        return new CalibrationResultCalculator(calibrations, baseOperations).getResult();
    }

    protected List<Calibration> readCalibrations() {
        return Inputs.readStrings("Day07")
                .stream()
                .map(Calibration::new)
                .toList();
    }

    static class CalibrationResultCalculator {
        private final List<Calibration> calibrations;
        private final Set<Calibration> validCalibrations = new HashSet<>();
        private final List<Function<BigDecimal[], BigDecimal>> operations;

        CalibrationResultCalculator(List<Calibration> calibrations, List<Function<BigDecimal[], BigDecimal>> operations) {
            this.calibrations = calibrations;
            this.operations = operations;
        }

        String getResult() {
            findValid();

            return calculateResult();
        }

        private void findValid() {
            this.calibrations.forEach(this::findValid);
        }

        private String calculateResult() {
            List<BigDecimal> results = validCalibrations.stream().map(c -> c.result).toList();
            BigDecimal sum = new BigDecimal(0);
            for (BigDecimal result : results) {
                sum = sum.add(result);
            }
            return sum.toBigInteger().toString();
        }

        private void findValid(Calibration c) {
            List<BigDecimal> remaining = new ArrayList<>(c.arguments);
            BigDecimal current = remaining.removeFirst();
            findValid(c, current, remaining);
        }

        private void findValid(Calibration c, BigDecimal current, List<BigDecimal> remainingArgs) {
            if (!remainingArgs.isEmpty()) {
                List<BigDecimal> newArgs = new ArrayList<>(remainingArgs);
                BigDecimal arg = newArgs.removeFirst();
                for (Function<BigDecimal[], BigDecimal> operation : operations) {
                    BigDecimal newResult = operation.apply(new BigDecimal[]{current, arg});
                    if (newResult.compareTo(c.result) == 0 && newArgs.isEmpty()) {
                        validCalibrations.add(c);
                        break;
                    }
                    if (newResult.compareTo(c.result) > 0) {
                        continue;
                    }
                    findValid(c, newResult, newArgs);
                }
            }
        }
    }

    protected static class Calibration {
        String source;
        BigDecimal result;
        List<BigDecimal> arguments;

        public Calibration(String source) {
            this.source = source;
            String[] parts = source.split(": ");
            this.result = BigDecimal.valueOf(Double.parseDouble(parts[0]));
            this.arguments = Arrays.stream(parts[1].split(" "))
                    .map(s -> BigDecimal.valueOf(Double.parseDouble(s)))
                    .toList();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Calibration that = (Calibration) o;
            return Objects.equals(source, that.source);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(source);
        }

        @Override
        public String toString() {
            return this.source;
        }
    }
}
