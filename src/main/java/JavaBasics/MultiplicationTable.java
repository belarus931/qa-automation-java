package JavaBasics;

public class MultiplicationTable {
    public void printTable(int size){
        for (int i = 1; i<= size; i++){
            for (int j = 1; j<= size; j++) {
                System.out.print((i*j) + " ");
            }
            System.out.println();
        }
    }
}
