import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger ATOMIC_3 = new AtomicInteger(0);
    private static final AtomicInteger ATOMIC_4 = new AtomicInteger(0);
    private static final AtomicInteger ATOMIC_5 = new AtomicInteger(0);
    private static final int NUMBER_WORD = 100_000;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[NUMBER_WORD];

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        // TODO
        Thread threadID1 = new Thread(() -> {
            for (String text : texts) {
                if (istPalindrome(text)) {
                    incrementCount(text.length());
                }
            }
        });
        threadID1.start();

        Thread threadID2 = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetters(text)) {
                    incrementCount(text.length());
                }
            }
        });
        threadID2.start();

        Thread threadID3 = new Thread(() -> {
            for (String text : texts) {
                if (isOrder(text)) {
                    incrementCount(text.length());
                }
            }
        });
        threadID3.start();

        threadID1.join();
        threadID2.join();
        threadID3.join();

        System.out.printf("\nКрасивых слов с длиной 3: %d шт.", ATOMIC_3.get());
        System.out.printf("\nКрасивых слов с длиной 4: %d шт.", + ATOMIC_4.get());
        System.out.printf("\nКрасивых слов с длиной 5: %d шт.\n", + ATOMIC_5.get());
    }

    public static void incrementCount(int text) {
        switch (text) {
            case 3 -> ATOMIC_3.incrementAndGet();
            case 4 -> ATOMIC_4.incrementAndGet();
            case 5 -> ATOMIC_5.incrementAndGet();
        }
    }

    private static boolean istPalindrome(String s) { // "adda"
        String reverse = new StringBuilder(s).reverse().toString();
        return s.equals(reverse);
    }

    private static boolean isSameLetters(String s) { // "bbbb"
        for (byte i = 1; i < s.length(); i++) {
            if (s.charAt(0) != s.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOrder(String s) { // "aaccc"
        for (byte i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) > s.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}