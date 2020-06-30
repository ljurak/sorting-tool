package sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String dataType;
        if (args.length >= 2 && "-dataType".equals(args[0])) {
            dataType = args[1];
        } else {
            dataType = "word";
        }

        switch (dataType) {
            case "long":
                processLongs();
                break;
            case "line":
                processLines();
                break;
            case "word":
                processWords();
                break;
            default:
                throw new IllegalArgumentException("Unsupported argument: " + dataType);
        }
    }

    private static void processLongs() {
        List<Long> numbers = new ArrayList<>();
        while (scanner.hasNextLong()) {
            numbers.add(scanner.nextLong());
        }

        System.out.println("Total numbers: " + numbers.size() + ".");

        if (numbers.size() > 0) {
            Collections.sort(numbers);
            long max = numbers.get(numbers.size() - 1);
            long occurrences = countOccurrences(numbers, max);
            long percentage = Math.round(((double) occurrences / numbers.size()) * 100);
            System.out.println("The greatest number: " + max + " (" + occurrences + " time(s), " + percentage + "%).");
        }
    }

    private static void processLines() {
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        System.out.println("Total lines: " + lines.size() + ".");

        if (lines.size() > 0) {
            lines.sort(Comparator.comparingInt(String::length).thenComparing(String::compareTo));
            String max = lines.get(lines.size() - 1);
            long occurrences = countOccurrences(lines, max);
            long percentage = Math.round(((double) occurrences / lines.size()) * 100);
            System.out.println("The longest line:");
            System.out.println(max);
            System.out.println("(" + occurrences + " time(s), " + percentage + "%).");
        }
    }

    private static void processWords() {
        List<String> words = new ArrayList<>();
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }

        System.out.println("Total words: " + words.size() + ".");

        if (words.size() > 0) {
            words.sort(Comparator.comparingInt(String::length).thenComparing(String::compareTo));
            String max = words.get(words.size() - 1);
            long occurrences = countOccurrences(words, max);
            long percentage = Math.round(((double) occurrences / words.size()) * 100);
            System.out.println("The longest word: " + max + " (" + occurrences + " time(s), " + percentage + "%).");
        }
    }

    private static <T> long countOccurrences(List<T> elements, T value) {
        long count = 0;
        for (T element : elements) {
            if (element.equals(value)) {
                count++;
            }
        }
        return count;
    }
}
