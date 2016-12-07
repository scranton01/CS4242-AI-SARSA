import java.util.Random;

public class Test {
    public static void main(String [] args){
//        GridWorld gridWorld = new GridWorld();
//        gridWorld.printGridWorld();

//        int [] coordinate = gridWorld.findPerson();
//        System.out.println("row: " +coordinate[0]+" column: "+coordinate[1]);
        Random random = new Random();
        int randomDirection = random.nextInt(4);
        switch (randomDirection){
            case 0:
                System.out.println("North");
//                break;
            case 1:
                System.out.println("East");
//                break;
            case 2:
                System.out.println("South");
//                break;
            case 3:
                System.out.println("West");
//                break;
        }
        System.out.println(random.nextDouble());
    }
}
