package JavaBasics;

public class Grade {
    public void getGrade(int score) {
        if (score >= 0 && score <= 5) {
            System.out.println("Grage " + score + " - Неудовлетворительно");
        } else if (score > 5 && score <= 7) {
            System.out.println("Grage " + score + " - Удовлетворительно");
        } else if (score == 8) {
            System.out.println("Grage " + score + " - Хорошо");
        } else if (score > 8) {
            System.out.println("Grage " + score + " - Отлично");
        }
    }
}
