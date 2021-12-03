import lettercount.LetterCounter;

public class Main {
    public static void main(String[] args) {
        System.out.println("Your Program is started");
        LetterCounter letterCounter = new LetterCounter(Main.class.getResource("hafez.txt").getPath());
        letterCounter.letterCount("Daftar Hafez");
    }
}
