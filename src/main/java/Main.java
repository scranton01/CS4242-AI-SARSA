public class Main {
    public static void main (String [] args){
        System.out.println("Grid World");
        GridWorld gridWorld = new GridWorld();
        gridWorld.printGridWorld();
//        while(true){
//            try {
//                TimeUnit.MILLISECONDS.sleep(500);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            gridWorld.travel();
//        }

//        while(true){
            gridWorld.eligibilityTraceLearning();
//        }

    }
}
