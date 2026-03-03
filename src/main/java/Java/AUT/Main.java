package Java.AUT;

import JavaBasics.*;

import java.util.*;


public class Main {
    public static void main(String[] args) {
//        SumCalculator calc = new SumCalculator();
//        System.out.println(calc.sumUpTo(-5));
//
//        Factorial f = new Factorial();
//        System.out.println(f.factorial(5));
//
//        MultiplicationTable mt = new MultiplicationTable();
//        mt.printTable(5);
//
//        ListProcessor list = new ListProcessor();
//        List<String> allNames = list.addNames("Александр", "Анна", "Дмитрий", "Ия", "Екатерина");
//        System.out.println(allNames);
//        List<String> filteredNames = list.filterLongNames(allNames,7);
//        System.out.println(filteredNames);

//        UniqueWords uw = new UniqueWords();
//        Set<String> words = uw.getUniqueWords("java python java cpp python java");
//        System.out.println(words); // [java, python, cpp] (порядок может быть любым)
//
//        WordCounter counter = new WordCounter();
//
//        String sentence = "apple banana apple orange banana apple";
//        Map<String, Integer> frequency = counter.countWords(sentence);
//
//        System.out.println("Предложение: " + sentence);
//        System.out.println("Частота слов: " + frequency);

        DuplicateFinder df = new DuplicateFinder();

        List<Integer> numbers = Arrays.asList(1, 2, 3, 2, 4, 5, 1, 6, 7, 1);
        Set<Integer> duplicates = df.findDuplicates(numbers);

        System.out.println("Исходный список: " + numbers);
        System.out.println("Дубликаты: " + duplicates); // [1, 2]
        }

//        Greeting gr = new Greeting();
//        gr.sayHello("Dmitry");

//        AgeCalculator a = new AgeCalculator();
//        a.calculateAge(1993,2026);
//
//        EvenOdd e = new EvenOdd();
//        e.checkEvenOdd(-4);
//
//        Grade g = new Grade();
//        g.getGrade(8);
//        g.getGrade(3);
//        g.getGrade(10);
//        g.getGrade(7);
//
//        WeekDay w = new WeekDay();
//        w.getDayName(1);
//        w.getDayName(7);
//        w.getDayName(-2);
//        w.getDayName(43);
//
//        LoginChecker l = new LoginChecker();
//        l.checkAccess(true,false);
//        l.checkAccess(true,true);
//
//        SimpleCalculator s = new SimpleCalculator();
//        s.calculate(5, 5, '*');
//        s.calculate(5, 5, '/');
//        s.calculate(5, 5, '-');
//        s.calculate(5, 5, '+');
    }
