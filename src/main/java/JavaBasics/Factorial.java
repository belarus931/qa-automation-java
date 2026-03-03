package JavaBasics;

public class Factorial {
    public long factorial(int n){
            long fac = 1;
            for (int i = 1; i<=n; i++){
            fac *=i;
        }
            return fac;
    }
}
