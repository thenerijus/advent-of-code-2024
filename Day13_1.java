import java.util.ArrayList;
import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/13">Day thirteen</a>
 *
 * @author Nerijus
 */
public class Day13_1 {
    public static void main() {
        System.out.println("Fewest tokens for all possible prizes: " + new Day13_1().getResult());
    }

    private int getResult() {
        return readClawMachines()
                .stream()
                .map(this::findSolutions)
                // find cheapest
                .mapToInt(solutions -> solutions
                        .stream()
                        .mapToInt(PrizeSolution::getPrice)
                        .min()
                        .orElse(0))
                .sum();
    }

    private List<PrizeSolution> findSolutions(ClawMachine clawMachine) {
        List<PrizeSolution> solutions = new ArrayList<>();
        // press A up to 100 times
        for (int a = 1; a <= 100; a++) {
            int xAfterA = a * clawMachine.aX;
            int yAfterA = a * clawMachine.aY;
            if (xAfterA > clawMachine.prizeX || yAfterA > clawMachine.prizeY) {
                // too far already, skip B check
                continue;
            }
            // press B up to 100 times
            for (int b = 1; b <= 100; b++) {
                int x = b * clawMachine.bX + xAfterA;
                int y = b * clawMachine.bY + yAfterA;
                if (x > clawMachine.prizeX || y > clawMachine.prizeY) {
                    // too far already
                    continue;
                }
                if (x == clawMachine.prizeX && y == clawMachine.prizeY) {
                    // found it
                    solutions.add(new PrizeSolution(a, b));
                }
            }
        }
        return solutions;
    }

    private List<ClawMachine> readClawMachines() {
        List<ClawMachine> clawMachines = new ArrayList<>();
        List<String> input = Inputs.readStrings("Day13").stream().filter(l -> !l.isEmpty()).toList();
        for (int i = 0; i < input.size(); i+=3) {
            clawMachines.add(new ClawMachine(input.get(i), input.get(i + 1), input.get(i + 2)));
        }
        return clawMachines;
    }

    static class PrizeSolution {
        int aPresses, bPresses;

        public PrizeSolution(int aPresses, int bPresses) {
            this.aPresses = aPresses;
            this.bPresses = bPresses;
        }

        int getPrice() {
            return aPresses * 3 + bPresses;
        }
    }

    static class ClawMachine {
        int aX, aY;
        int bX, bY;
        int prizeX, prizeY;

        public ClawMachine(String aChangeSource, String bChangeSource, String prizeSource) {
            String[] aChange = aChangeSource.replace("Button A: X+", "").replace(" Y+", "").split(",");
            String[] bChange = bChangeSource.replace("Button B: X+", "").replace(" Y+", "").split(",");
            String[] prizeLocation = prizeSource.replace("Prize: X=", "").replace(" Y=", "").split(",");

            this.aX = Integer.parseInt(aChange[0]);
            this.aY = Integer.parseInt(aChange[1]);
            this.bX = Integer.parseInt(bChange[0]);
            this.bY = Integer.parseInt(bChange[1]);
            this.prizeX = Integer.parseInt(prizeLocation[0]);
            this.prizeY = Integer.parseInt(prizeLocation[1]);
        }
    }
}
