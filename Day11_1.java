import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/11">Day eleven</a>
 *
 * @author Nerijus
 */
public class Day11_1 {
    public static void main() {
        System.out.println("Stones after blinking 25 times: " + new Day11_1().getResult());
    }

    private int getResult() {
        List<BigDecimal> stones = Arrays.stream(Inputs.readString("Day11").split(" ")).map(BigDecimal::new).toList();
        int blinks = 25;

        for (int i = 1; i <= blinks; i++) {
            System.out.println("Blink: " + i);
            List<BigDecimal> newStones = new ArrayList<>();
            for (BigDecimal stone : stones) {
                if (stone.equals(BigDecimal.ZERO)) {
                    newStones.add(BigDecimal.ONE);
                } else if (hasEvenNumberOfDigits(stone)) {
                    String number = stone.toBigInteger().toString();
                    int midpoint = number.length() / 2;
                    newStones.add(new BigDecimal(number.substring(0, midpoint)));
                    newStones.add(new BigDecimal(number.substring(midpoint)));
                } else {
                    newStones.add(stone.multiply(new BigDecimal(2024)));
                }
            }
            stones = newStones;
            System.out.println(newStones.size());
        }
        return stones.size();
    }

    private boolean hasEvenNumberOfDigits(BigDecimal stone) {
        return stone.toBigInteger().toString().length() % 2 == 0;
    }
}
