import lombok.Data;

import java.util.Random;


@Data
public class Grid {
    int row;
    int column;
    double upWeight;
    double rightWeight;
    double downWeight;
    double leftWeight;
    State state;
    double stateReward;
    double epsilon;

    public Grid(int r, int c) {
        this.row = r;
        this.column = c;
        this.upWeight = 0;
        this.rightWeight = 0;
        this.downWeight = 0;
        this.leftWeight = 0;
        this.epsilon = 0.2;
    }

    public enum Direction {
        UP,
        LEFT,
        DOWN,
        RIGHT
    }

    public Direction getDirection() {
        double max = upWeight;
        Random random = new Random();
        if(random.nextDouble()>epsilon) {
            Direction direction = Direction.UP;
            if (rightWeight > max) {
                max = rightWeight;
                direction = Direction.RIGHT;
            }
            if (downWeight > max) {
                max = downWeight;
                direction = Direction.DOWN;
            }
            if (leftWeight > max) {
                direction = Direction.LEFT;
            }
            return direction;
        }else{
            int randomDirection = random.nextInt(4);
            switch (randomDirection){
                case 0:
                    return Direction.UP;
                case 1:
                    return Direction.RIGHT;
                case 2:
                    return Direction.DOWN;
                case 3:
                    return Direction.LEFT;
            }
        }
        return null;
    }

    public Double getStateReward() {
        return stateReward;
    }

    public void setWeight(Direction direction, double value) {
        switch (direction) {
            case UP:
                upWeight = value;
                break;
            case RIGHT:
                rightWeight = value;
                break;
            case DOWN:
                downWeight = value;
                break;
            case LEFT:
                leftWeight = value;
                break;
        }
    }

    public Double getWeight(Direction direction) {
        switch (direction) {
            case UP:
                return upWeight;
            case RIGHT:
                return rightWeight;
            case DOWN:
                return downWeight;
            case LEFT:
                return leftWeight;
        }
        return null;
    }
}
