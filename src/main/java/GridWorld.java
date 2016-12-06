import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridWorld {
    List<List<Grid>> table;
    JFrame frame;

    GridWorld() {
        this.table = new ArrayList<>();
        for (int i = 0; i < 22; i++) {
            List<Grid> row = new ArrayList<>();
            for (int j = 0; j < 22; j++) {
                Grid grid = new Grid();
                if (i == 0 || i == 21 || j == 0 || j == 21) {
                    grid.setState(State.PIT);
                } else {
                    grid.setState(State.BLANK);
                }
                row.add(grid);
            }
            this.table.add(row);
        }

        setGoal();
        setPerson();
        generateRandomWeight();
        this.frame = new JFrame("GridWorld");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(1050, 1050));
    }

    private void generateRandomWeight() {
        Random random = new Random();
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                get(i, j).setUpWeight(random.nextDouble() / 100);
                get(i, j).setRightWeight(random.nextDouble() / 100);
                get(i, j).setDownWeight(random.nextDouble() / 100);
                get(i, j).setLeftWeight(random.nextDouble() / 100);
            }
        }
    }

    private void setGoal() {
        boolean isPit = true;
        Random random = new Random();
        while (isPit) {
            int row = random.nextInt(22);
            int column = random.nextInt(22);
            if (get(row, column).getState() == State.BLANK) {
                get(row, column).setState(State.GOAL);
                isPit = false;
            } else {
                isPit = true;
            }
        }
    }

    private void setPerson() {
        boolean isPit = true;
        Random random = new Random();
        while (isPit) {
            int row = random.nextInt(22);
            int column = random.nextInt(22);
            if (get(row, column).getState() == State.BLANK) {
                get(row, column).setState(State.PERSON);
                isPit = false;
            } else {
                isPit = true;
            }
        }
    }

    private Grid get(int row, int column) {
        return table.get(row).get(column);
    }

    void printGridWorld() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        ImageIcon up = new ImageIcon(classLoader.getResource("up.jpg"));
        ImageIcon right = new ImageIcon(classLoader.getResource("right.jpg"));
        ImageIcon down = new ImageIcon(classLoader.getResource("down.jpg"));
        ImageIcon left = new ImageIcon(classLoader.getResource("left.jpg"));
        ImageIcon pit = new ImageIcon(classLoader.getResource("pit.jpg"));
        ImageIcon treasure = new ImageIcon(classLoader.getResource("treasure.jpg"));
        ImageIcon person = new ImageIcon(classLoader.getResource("link.jpg"));

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(22, 22));
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                State state = get(i, j).getState();
                switch (state) {
                    case BLANK:
                        Grid.Direction direction = get(i, j).getDirection();
                        switch (direction) {
                            case UP:
                                grid.add(new JLabel(up));
                                break;
                            case RIGHT:
                                grid.add(new JLabel(right));
                                break;
                            case DOWN:
                                grid.add(new JLabel(down));
                                break;
                            case LEFT:
                                grid.add(new JLabel(left));
                                break;
                        }
                        break;
                    case PIT:
                        grid.add(new JLabel(pit));
                        break;
                    case GOAL:
                        grid.add(new JLabel(treasure));
                        break;
                    case PERSON:
                        grid.add(new JLabel(person));
                }
            }
        }
        frame.add(grid);
        frame.pack();
        frame.setVisible(true);
    }

    void travel() {
        Grid person = new Grid();
        int row = 0;
        int column = 0;
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                if (get(i, j).getState() == State.PERSON) {
                    person = get(i, j);
                    row = i;
                    column = j;
                }
            }
        }
        Grid.Direction direction = person.getDirection();
        switch (direction) {
            case UP:
                get(row, column + 1).setState(State.PERSON);
                break;
            case RIGHT:
                get(row + 1, column).setState(State.PERSON);
                break;
            case DOWN:
                get(row, column - 1).setState(State.PERSON);
                break;
            case LEFT:
                get(row - 1, column).setState(State.PERSON);
                break;
        }
        get(row,column).setState(State.BLANK);
        printGridWorld();
    }
}
