import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/9">Day nine</a>
 *
 * @author Nerijus
 */
public class Day09_1 {
    public static void main() {
        System.out.println("Resulting filesystem checksum: " + new Day09_1().getResult());
    }

    private String getResult() {
        List<Block> memory = readMemory();
        BigDecimal checksum = new BigDecimal(0);
        int position = -1;
        int currentFileId = 0;
        int remainingFile = 0;
        while (!memory.isEmpty()) {
            Block block = memory.removeFirst();
            if (block instanceof FileBlock file) {
                for (int i = 0; i < file.size; i++) {
                    position += 1;
                    checksum = checksum.add(new BigDecimal(file.id * position));
                }
            } else {
                SpaceBlock space = (SpaceBlock) block;
                for (int i = 0; i < space.size; i++) {
                    position += 1;
                    // find file to fill it with
                    if (remainingFile == 0) {
                        // find file from the end
                        Block last = memory.removeLast();
                        if (!(last instanceof FileBlock)) {
                            // got space block, next is file
                            last = memory.removeLast();
                        }
                        currentFileId = ((FileBlock)last).id;
                        remainingFile = last.size;
                    }
                    remainingFile--;
                    checksum = checksum.add(new BigDecimal(currentFileId * position));
                }
                if (memory.isEmpty() && remainingFile > 0) {
                    for (int i = 0; i < remainingFile; i++) {
                        position += 1;
                        checksum = checksum.add(new BigDecimal(currentFileId * position));
                    }
                }
            }
        }
        return checksum.toBigInteger().toString();
    }

    protected List<Block> readMemory() {
        List<Block> memory = new ArrayList<>();
        String[] memorySource = Inputs.readString("Day09").split("");
        int index = 0;
        for (int i = 0; i < memorySource.length; i += 2) {
            memory.add(new FileBlock(index, Integer.parseInt(memorySource[i])));
            index++;
            if (memorySource.length != i + 1) {
                memory.add(new SpaceBlock(Integer.parseInt(memorySource[i + 1])));
            }
        }
        return memory;
    }

    protected static class Block {
        int size;
    }

    protected static class FileBlock extends Block {
        int id;
        boolean processed;

        public FileBlock(int id, int size) {
            this.id = id;
            this.size = size;
        }

        @Override
        public String toString() {
            return "File(" +
                    "id=" + id +
                    ", size=" + size +
                    ')';
        }
    }

    protected static class SpaceBlock extends Block {
        public SpaceBlock(int size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "Space(" +
                    "size=" + size +
                    ')';
        }
    }
}
