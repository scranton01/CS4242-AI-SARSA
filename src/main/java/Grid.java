import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Grid {
    double upWeight;
    double rightWeight;
    double downWeight;
    double leftWeight;
    State state;

    public enum Direction{
        UP,
        LEFT,
        DOWN,
        RIGHT
    }
    public Direction getDirection(){
        double max = upWeight;
        Direction direction = Direction.UP;
        if(rightWeight > max){
            max = rightWeight;
            direction = Direction.RIGHT;
        }
        if(downWeight > max){
            max = downWeight;
            direction = Direction.DOWN;
        }
        if(leftWeight > max){
            direction = Direction.LEFT;
        }
        return direction;

    }
}
