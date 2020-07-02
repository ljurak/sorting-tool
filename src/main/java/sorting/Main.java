package sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final Arguments arguments = new Arguments();

    public static void main(String[] args) {
        arguments.processArguments(args);

        if (!arguments.isValidArguments()) {
            return;
        }

        switch (arguments.getDataType()) {
            case "long":
                processLongs();
                break;
            case "line":
                processLines();
                break;
            case "word":
                processWords();
                break;
        }
    }

    private static void processLongs() {
        List<Long> numbers = new ArrayList<>();
        while (scanner.hasNext()) {
            String next = scanner.next();
            try {
                numbers.add(Long.parseLong(next));
            } catch (NumberFormatException e) {
                System.out.println("\"" + next + "\" isn't a long. It's skipped.");
            }
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
        String sortingType = arguments.getSortingType();
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
        data.forEach(el -> result.merge(el, 1, Integer::sum));
        return result;
    }
}
