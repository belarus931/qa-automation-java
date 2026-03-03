package JavaBasics;

public class SumCalculator {
    int i = 0;

    public int sumUpTo(int n){
        int sum = 0;
        while ( n > 0){
            sum += n;
            n--;
        }
        return sum;
    }
}
