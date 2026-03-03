package JavaBasics;

public class SimpleCalculator {
    public void calculate(int a, int b, char operation) {
        if (operation == '*') {
            System.out.println(a + " * " + b + " = " + (a * b));
        } else if (operation == '/') {
            System.out.println(a + " / " + b + " = " + (a / b));
        } else if (operation == '+') {
            System.out.println(a + " + " + b + " = " + (a + b));
        } else if (operation == '-') {
            System.out.println(a + " - " + b + " = " + (a - b));
        }
            else System.out.println("Unknown operation");
        }
    }

