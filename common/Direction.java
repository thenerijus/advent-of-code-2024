package common;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction from(String arrow) {
        return switch (arrow) {
            case "^" -> UP;
            case "v" -> DOWN;
            case "<" -> LEFT;
            case ">" -> RIGHT;
            default -> throw new IllegalArgumentException("Invalid direction: " + arrow);
        };
    }

    public Direction rotateLeft() {
        return switch (this) {
            case UP -> LEFT;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case RIGHT -> UP;
        };
    }

    public Direction rotateRight() {
        return switch (this) {
            case UP -> RIGHT;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case RIGHT -> DOWN;
        };
    }
}
