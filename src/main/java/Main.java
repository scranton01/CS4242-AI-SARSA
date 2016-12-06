public class Main {
    public static void main (String [] args){
        System.out.println("Grid World");
        GridWorld gridWorld = new GridWorld();
        gridWorld.printGridWorld();
        while(true){
            gridWorld.travel();
        }


    }
}
