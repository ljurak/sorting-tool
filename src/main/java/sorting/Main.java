package sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static String dataType = "word";

    private static String sortingType = "natural";

    public static void main(String[] args) {
        processArguments(args);

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
                throw new IllegalArgumentException("Unsupported dataType value: " + dataType);
        }
    }

    private static void processArguments(String[] args) {
        String currentArg = null;
        for (String arg : args) {
            if (arg.startsWith("-")) {
                currentArg = arg;
            } else {
                assignArgument(currentArg, arg);
            }
        }

        if (!(sortingType.equals("natural") || sortingType.equals("byCount"))) {
            throw new IllegalArgumentException("Unsupported sortingType value: " + sortingType);
        }
    }

    private static void assignArgument(String argName, String argValue) {
        if (argName == null) {
            return;
        }

        if ("-dataType".equals(argName)) {
            dataType = argValue;
        } else if ("-sortingType".equals(argName)) {
            sortingType = argValue;
        }
    }

    private static void processLongs() {
        List<Long> numbers = new ArrayList<>();
        while (scanner.hasNextLong()) {
            numbers.add(scanner.nextLong());
        }

        System.out.println("Total numbers: " + numbers.size() + ".");
        processSorting(numbers, false);
    }

    private static void processLines() {
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        System.out.println("Total lines: " + lines.size() + ".");
        processSorting(lines, true);
    }

    private static void processWords() {
        List<String> words = new ArrayList<>();
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }

        System.out.println("Total words: " + words.size() + ".");
        processSorting(words, false);
    }

    private static <T extends Comparable<? super T>> void processSorting(List<T> data, boolean printMultiline) {
        if (data.size() > 0) {
            if ("natural".equals(sortingType)) {
                Collections.sort(data);
                System.out.print(printMultiline ? "Sorted data: " + System.lineSeparator() : "Sorted data: ");
                for (T elem : data) {
                    System.out.print(printMultiline ? elem + System.lineSeparator() : elem + " ");
                }
            } else if ("byCount".equals(sortingType)) {
                Map<T, Integer> sorted = sortByCount(data);
                sorted.entrySet().stream()
                        .sorted((first, second) -> first.getValue().equals(second.getValue())
                                ? first.getKey().compareTo(second.getKey())
                                : first.getValue() - second.getValue())
                        .forEach(entry -> {
                            long percentage = Math.round(((double) entry.getValue() / data.size()) * 100);
                            System.out.println(entry.getKey() + ": " + entry.getValue() + " time(s), " + percentage + "%).");
                        });
            }
        }
    }

    private static <T extends Comparable<? super T>> Map<T, Integer> sortByCount(List<T> data) {
        Map<T, Integer> result = new HashMap<>();
        data.forEach(el -> result.compute(el, (key, value) -> value == null ? 1 : value + 1));
        return result;
    }
}
