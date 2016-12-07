import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GridWorld {
    List<List<Grid>> table;
    List<List<Grid>> eTable;
    double gamma = 0.5;
    double learningRate = 0.5;
    double lambda = 0.9;
    JFrame frame;

    GridWorld() {
        this.table = new ArrayList<>();
        for (int i = 0; i < 22; i++) {
            List<Grid> row = new ArrayList<>();
            for (int j = 0; j < 22; j++) {
                Grid grid = new Grid(i, j);
                if (i == 0 || i == 21 || j == 0 || j == 21) {
                    grid.setState(State.PIT);
                } else {
                    grid.setState(State.BLANK);
                }
                row.add(grid);
            }
            this.table.add(row);
        }

        setObstacle();
        setGoal();
        setPerson();
        generateRandomWeight();
        setStateReward();
        this.frame = new JFrame("GridWorld");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(1050, 1050));
    }

    private void resetGridWorld() {
        for (int i = 0; i < 22; i++) {
            List<Grid> row = new ArrayList<>();
            for (int j = 0; j < 22; j++) {
                Grid grid = new Grid(i, j);
                if (i == 0 || i == 21 || j == 0 || j == 21) {
                    grid.setState(State.PIT);
                } else if (!get(i, j).getState().equals(State.GOAL)) {
                    grid.setState(State.BLANK);
                }
                row.add(grid);
            }
            this.table.add(row);
        }
        setObstacle();
        setPerson();
    }

    private void setStateReward() {
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                switch (get(i, j).getState()) {
                    case GOAL:
                        get(i, j).setStateReward(1);
                        break;
                    case PIT:
                        get(i, j).setStateReward(-1);
                        break;
                    default:
                        get(i, j).setStateReward(0);
                        break;
                }
            }
        }
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

    private void setObstacle() {
        get(15, 12).setState(State.PIT);
        get(15, 13).setState(State.PIT);
        get(15, 14).setState(State.PIT);
        get(14, 14).setState(State.PIT);
        get(13, 14).setState(State.PIT);

        get(14, 4).setState(State.PIT);
        get(15, 4).setState(State.PIT);
        get(16, 4).setState(State.PIT);
        get(17, 4).setState(State.PIT);
        get(17, 5).setState(State.PIT);
        get(17, 6).setState(State.PIT);
        get(17, 7).setState(State.PIT);
        get(17, 8).setState(State.PIT);
        get(17, 9).setState(State.PIT);

        get(5, 15).setState(State.PIT);
        get(5, 16).setState(State.PIT);
        get(5, 17).setState(State.PIT);
        get(6, 15).setState(State.PIT);
        get(6, 16).setState(State.PIT);
        get(6, 17).setState(State.PIT);
        get(7, 15).setState(State.PIT);
        get(7, 16).setState(State.PIT);
        get(7, 17).setState(State.PIT);

        get(8, 4).setState(State.PIT);
        get(8, 5).setState(State.PIT);
        get(8, 6).setState(State.PIT);
        get(7, 6).setState(State.PIT);
        get(6, 6).setState(State.PIT);
        get(5, 6).setState(State.PIT);
        get(5, 7).setState(State.PIT);
        get(5, 8).setState(State.PIT);
        get(5, 9).setState(State.PIT);
        get(4, 9).setState(State.PIT);
        get(3, 9).setState(State.PIT);
        get(3, 10).setState(State.PIT);
        get(3, 11).setState(State.PIT);
        get(3, 12).setState(State.PIT);

        get(18, 13).setState(State.PIT);
        get(18, 14).setState(State.PIT);
        get(18, 15).setState(State.PIT);
        get(18, 16).setState(State.PIT);
        get(18, 17).setState(State.PIT);
        get(18, 18).setState(State.PIT);
        get(17, 18).setState(State.PIT);
        get(16, 18).setState(State.PIT);
        get(15, 18).setState(State.PIT);
        get(14, 18).setState(State.PIT);
        get(13, 18).setState(State.PIT);
        get(12, 18).setState(State.PIT);
        get(11, 18).setState(State.PIT);
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

    private Grid getETable(int row, int column) {
        return eTable.get(row).get(column);
    }

    void printGridWorld() {
//        frame.removeAll();
//        SwingUtilities.updateComponentTreeUI(frame);
//        frame.invalidate();
//        frame.validate();
//        frame.repaint();
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

    Grid findPerson() {
        int[] coordinate = new int[2];
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                if (get(i, j).getState() == State.PERSON) {
                    return get(i, j);
                }
            }
        }
        return null;
    }

    private void movePerson(Grid.Direction direction) {
        Grid person = findPerson();
        person.setState(State.BLANK);
        switch (direction) {
            case UP:
                get(person.getRow() - 1, person.getColumn()).setState(State.PERSON);
                break;
            case RIGHT:
                get(person.getRow(), person.getColumn() + 1).setState(State.PERSON);
                break;
            case DOWN:
                get(person.getRow() + 1, person.getColumn()).setState(State.PERSON);
                break;
            case LEFT:
                get(person.getRow(), person.getColumn() - 1).setState(State.PERSON);
                break;
        }
    }

    private Grid getDestinationGrid(Grid.Direction direction) {
        Grid person = findPerson();
        switch (direction) {
            case UP:
                return get(person.getRow() - 1, person.getColumn());
            case RIGHT:
                return get(person.getRow(), person.getColumn() + 1);
            case DOWN:
                return get(person.getRow() + 1, person.getColumn());
            case LEFT:
                return get(person.getRow(), person.getColumn() - 1);
        }
        return null;
    }


    void eligibilityTraceLearning() {
        int iteration = 0;
        while (true) {
            System.out.println("episode: " + iteration++);
            boolean isTerminal = false;
            eTable = new ArrayList<>();
            //initializing e table
            for (int i = 0; i < 22; i++) {
                List<Grid> row = new ArrayList<>();
                for (int j = 0; j < 22; j++) {
                    Grid grid = new Grid(i, j);
                    row.add(grid);
                }
                eTable.add(row);
            }
            Grid person = findPerson();
            Grid.Direction direction = person.getDirection();
            A:
            while (true) {
                Grid personPrime = getDestinationGrid(direction);
                Grid.Direction directionPrime = personPrime.epsilonGreedy();
                Double reward = personPrime.getStateReward();
                Double QPrimeValue = personPrime.getWeight(directionPrime);
                Double QValue = person.getWeight(direction);

                double delta = reward + gamma * (QPrimeValue - QValue);
                double eWeight = eTable.get(person.getRow()).get(person.getColumn()).getWeight(direction);
                getETable(person.getRow(), person.getColumn()).setWeight(direction, eWeight + 1);

                for (int i = 0; i < 22; i++) {
                    for (int j = 0; j < 22; j++) {
                        Grid iterator = get(i, j);
                        iterator.setUpWeight(iterator.getUpWeight() + (learningRate * delta * getETable(i, j).getUpWeight()));
                        iterator.setRightWeight(iterator.getRightWeight() + (learningRate * delta * getETable(i, j).getRightWeight()));
                        iterator.setDownWeight(iterator.getDownWeight() + (learningRate * delta * getETable(i, j).getDownWeight()));
                        iterator.setLeftWeight(iterator.getLeftWeight() + (learningRate * delta * getETable(i, j).getLeftWeight()));

                        Grid eTableIterator = getETable(i, j);
                        eTableIterator.setUpWeight(gamma * lambda * eTableIterator.getUpWeight());
                        eTableIterator.setRightWeight(gamma * lambda * eTableIterator.getRightWeight());
                        eTableIterator.setDownWeight(gamma * lambda * eTableIterator.getDownWeight());
                        eTableIterator.setLeftWeight(gamma * lambda * eTableIterator.getLeftWeight());
                    }
                }
                if (personPrime.getState() == State.GOAL || personPrime.getState() == State.PIT) {
                    person.setState(State.BLANK);
                    if (personPrime.getState() == State.GOAL) {
                        printGoal();
                    } else if (personPrime.getState() == State.PIT) {
                        printPit();
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break A;
                }
                movePerson(direction);
                person = personPrime;
                direction = directionPrime;
                printGridWorld();
                printETable();
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            resetGridWorld();
        }
    }

    private void printETable() {
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                if (getETable(i, j).getUpWeight() != 0 || getETable(i, j).getRightWeight() != 0 || getETable(i, j).getDownWeight() != 0 || getETable(i, j).getLeftWeight() != 0) {
                    System.out.print("[*]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }

    private void printGoal() {
        System.out.println("          _____                   _______                   _____                    _____          \n" +
                "         /\\    \\                 /::\\    \\                 /\\    \\                  /\\    \\         \n" +
                "        /::\\    \\               /::::\\    \\               /::\\    \\                /::\\____\\        \n" +
                "       /::::\\    \\             /::::::\\    \\             /::::\\    \\              /:::/    /        \n" +
                "      /::::::\\    \\           /::::::::\\    \\           /::::::\\    \\            /:::/    /         \n" +
                "     /:::/\\:::\\    \\         /:::/~~\\:::\\    \\         /:::/\\:::\\    \\          /:::/    /          \n" +
                "    /:::/  \\:::\\    \\       /:::/    \\:::\\    \\       /:::/__\\:::\\    \\        /:::/    /           \n" +
                "   /:::/    \\:::\\    \\     /:::/    / \\:::\\    \\     /::::\\   \\:::\\    \\      /:::/    /            \n" +
                "  /:::/    / \\:::\\    \\   /:::/____/   \\:::\\____\\   /::::::\\   \\:::\\    \\    /:::/    /             \n" +
                " /:::/    /   \\:::\\ ___\\ |:::|    |     |:::|    | /:::/\\:::\\   \\:::\\    \\  /:::/    /              \n" +
                "/:::/____/  ___\\:::|    ||:::|____|     |:::|    |/:::/  \\:::\\   \\:::\\____\\/:::/____/               \n" +
                "\\:::\\    \\ /\\  /:::|____| \\:::\\    \\   /:::/    / \\::/    \\:::\\  /:::/    /\\:::\\    \\               \n" +
                " \\:::\\    /::\\ \\::/    /   \\:::\\    \\ /:::/    /   \\/____/ \\:::\\/:::/    /  \\:::\\    \\              \n" +
                "  \\:::\\   \\:::\\ \\/____/     \\:::\\    /:::/    /             \\::::::/    /    \\:::\\    \\             \n" +
                "   \\:::\\   \\:::\\____\\        \\:::\\__/:::/    /               \\::::/    /      \\:::\\    \\            \n" +
                "    \\:::\\  /:::/    /         \\::::::::/    /                /:::/    /        \\:::\\    \\           \n" +
                "     \\:::\\/:::/    /           \\::::::/    /                /:::/    /          \\:::\\    \\          \n" +
                "      \\::::::/    /             \\::::/    /                /:::/    /            \\:::\\    \\         \n" +
                "       \\::::/    /               \\::/____/                /:::/    /              \\:::\\____\\        \n" +
                "        \\::/____/                 ~~                      \\::/    /                \\::/    /        \n" +
                "                                                           \\/____/                  \\/____/         \n" +
                "                                                                                                    ");
    }

    private void printPit() {
        System.out.println("          _____                    _____                _____          \n" +
                "         /\\    \\                  /\\    \\              /\\    \\         \n" +
                "        /::\\    \\                /::\\    \\            /::\\    \\        \n" +
                "       /::::\\    \\               \\:::\\    \\           \\:::\\    \\       \n" +
                "      /::::::\\    \\               \\:::\\    \\           \\:::\\    \\      \n" +
                "     /:::/\\:::\\    \\               \\:::\\    \\           \\:::\\    \\     \n" +
                "    /:::/__\\:::\\    \\               \\:::\\    \\           \\:::\\    \\    \n" +
                "   /::::\\   \\:::\\    \\              /::::\\    \\          /::::\\    \\   \n" +
                "  /::::::\\   \\:::\\    \\    ____    /::::::\\    \\        /::::::\\    \\  \n" +
                " /:::/\\:::\\   \\:::\\____\\  /\\   \\  /:::/\\:::\\    \\      /:::/\\:::\\    \\ \n" +
                "/:::/  \\:::\\   \\:::|    |/::\\   \\/:::/  \\:::\\____\\    /:::/  \\:::\\____\\\n" +
                "\\::/    \\:::\\  /:::|____|\\:::\\  /:::/    \\::/    /   /:::/    \\::/    /\n" +
                " \\/_____/\\:::\\/:::/    /  \\:::\\/:::/    / \\/____/   /:::/    / \\/____/ \n" +
                "          \\::::::/    /    \\::::::/    /           /:::/    /          \n" +
                "           \\::::/    /      \\::::/____/           /:::/    /           \n" +
                "            \\::/____/        \\:::\\    \\           \\::/    /            \n" +
                "             ~~               \\:::\\    \\           \\/____/             \n" +
                "                               \\:::\\    \\                              \n" +
                "                                \\:::\\____\\                             \n" +
                "                                 \\::/    /                             \n" +
                "                                  \\/____/                              \n" +
                "                                                                       ");
    }
}

