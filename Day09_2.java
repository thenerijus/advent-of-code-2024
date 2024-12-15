import java.math.BigDecimal;
import java.util.List;

/**
 * Part two of
 * <a href="https://adventofcode.com/2024/day/9">Day nine</a>
 *
 * @author Nerijus
 */
public class Day09_2 extends Day09_1 {
    public static void main() {
        System.out.println("Resulting filesystem checksum: " + new Day09_2().getResult());
    }

    private String getResult() {
        List<Block> memory = readMemory();
        // while we have files we did not check
        while (memory.stream().anyMatch(b -> b instanceof FileBlock && !((FileBlock)b).processed)) {
            // find file
            FileBlock file = (FileBlock) memory.reversed()
                    .stream()
                    .filter(b -> b instanceof FileBlock)
                    .filter(b -> !((FileBlock)b).processed)
                    .findFirst()
                    .orElseThrow();
            // look for empty space for it
            SpaceBlock space = (SpaceBlock) memory.stream()
                    .filter(b -> b instanceof SpaceBlock && b.size >= file.size)
                    // must be before file
                    .filter(b -> memory.indexOf(b) < memory.indexOf(file))
                    .findFirst()
                    .orElse(null);

            file.processed = true;
            if (space != null) {
                // space can fit file
                int spacePosition = memory.indexOf(space);
                memory.remove(space);

                if (space.size > file.size) {
                    // we also create new empty space
                    memory.add(spacePosition, new SpaceBlock(space.size - file.size));
                }
                int filePosition = memory.indexOf(file);
                memory.remove(file);
                memory.add(filePosition, new SpaceBlock(file.size));
                memory.add(spacePosition, file);
            }
        }

        return calculateChecksum(memory).toBigInteger().toString();
    }

    private static BigDecimal calculateChecksum(List<Block> memory) {
        int position = -1;
        BigDecimal checksum = new BigDecimal(0);
        for (Block block : memory) {
            if (block instanceof FileBlock file) {
                for (int i = 0; i < file.size; i++) {
                    position++;
                    checksum = checksum.add(new BigDecimal(file.id * position));
                }
            } else {
                // advance position
                position = position + block.size;
            }
        }
        return checksum;
    }
}
