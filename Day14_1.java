import common.Coordinates;

import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2024/day/14">Day fourteen</a>
 *
 * @author Nerijus
 */
public class Day14_1 {
    protected int MAX_X = 101;
    protected int MAX_Y = 103;

    int DURATION = 100;

    public static void main() {
        System.out.println("Safety factor: " + new Day14_1().getResult());
    }

    private long getResult() {
        List<Robot> robots = readRobots();
        for (int i = 0; i < DURATION; i++) {
            robots.forEach(r -> r.move(MAX_X, MAX_Y));
        }
        return inQuadrant1(robots) * inQuadrant2(robots) * inQuadrant3(robots) * inQuadrant4(robots);
    }

    protected List<Robot> readRobots() {
        return Inputs.readStrings("Day14").stream().map(Robot::new).toList();
    }

    private long inQuadrant1(List<Robot> robots) {
        return robots.stream().filter(r -> r.position.x < MAX_X / 2 && r.position.y < MAX_Y / 2).count();
    }

    private long inQuadrant2(List<Robot> robots) {
        return robots.stream().filter(r -> r.position.x > MAX_X / 2 && r.position.y < MAX_Y / 2).count();
    }

    private long inQuadrant3(List<Robot> robots) {
        return robots.stream().filter(r -> r.position.x < MAX_X / 2 && r.position.y > MAX_Y / 2).count();
    }

    private long inQuadrant4(List<Robot> robots) {
        return robots.stream().filter(r -> r.position.x > MAX_X / 2 && r.position.y > MAX_Y / 2).count();
    }

    protected static class Robot {
        public Robot(String source) {
            String[] parts = source.split(" ");
            String[] positionParts = parts[0].replace("p=", "").split(",");
            String[] velocityParts = parts[1].replace("v=", "").split(",");

            this.position = new Coordinates(Integer.parseInt(positionParts[0]), Integer.parseInt(positionParts[1]));
            this.velocityX = Integer.parseInt(velocityParts[0]);
            this.velocityY = Integer.parseInt(velocityParts[1]);
        }

        Coordinates position;
        int velocityX;
        int velocityY;

        void move(int maxX, int maxY) {
            int newX = position.x + velocityX;
            int newY = position.y + velocityY;

            if (newX < 0) {
                newX = maxX + (velocityX + position.x);
            } else if (newX >= maxX) {
                newX = (position.x + velocityX) - maxX;
            }

            if (newY < 0) {
                newY = maxY + (velocityY + position.y);
            } else if (newY >= maxY) {
                newY = (position.y + velocityY) - maxY;
            }

            this.position = new Coordinates(newX, newY);
        }
    }
}