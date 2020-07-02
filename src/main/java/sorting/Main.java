package sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static String[] validArgumentNames = new String[] {"-dataType", "-sortingType"};

    private static String[] validDataTypes = new String[] {"long", "line", "word"};

    private static String[] validSortingTypes = new String[] {"natural", "byCount"};

    private static boolean validArguments = true;

    private static String dataType = "word";

    private static String sortingType = "natural";

    public static void main(String[] args) {
        processArguments(args);

        if (!validArguments) {
            return;
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
        }
    }

    private static void processArguments(String[] arguments) {
        String currentArgumentName = null;
        boolean isArgumentValueExpected = false;

        for (String argument : arguments) {
            if (isArgumentValueExpected) {
                if (argument.startsWith("-")) {
                    showMissingArgument(currentArgumentName);
                    validArguments = false;
                    return;
                } else {
                    assignArgument(currentArgumentName, argument);
                    isArgumentValueExpected = false;
                }
            } else {
                if (argument.startsWith("-")) {
                    if (validateArgumentName(argument)) {
                        currentArgumentName = argument;
                        isArgumentValueExpected = true;
                    } else {
                        System.out.println(argument + " isn't a valid parameter. It's skipped.");
                    }
                }
            }
        }

        if (isArgumentValueExpected) {
            showMissingArgument(currentArgumentName);
            validArguments = false;
        }
    }

    private static boolean validateArgumentName(String name) {
        for (String validArgument : validArgumentNames) {
            if (validArgument.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateArgumentValue(String name, String value) {
        if ("-dataType".equals(name)) {
            return arrayContains(validDataTypes, value);
        } else if ("-sortingType".equals(name)) {
            return arrayContains(validSortingTypes, value);
        }
        return false;
    }

    private static void assignArgument(String name, String value) {
        if (validateArgumentValue(name, value)) {
            if ("-dataType".equals(name)) {
                dataType = value;
            } else if ("-sortingType".equals(name)) {
                sortingType = value;
            }
        }
    }

    private static void showMissingArgument(String argument) {
        if ("-dataType".equals(argument)) {
            System.out.println("No data type defined!");
        } else if ("-sortingType".equals(argument)) {
            System.out.println("No sorting type defined!");
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

    private static <T> boolean arrayContains(T[] array, T value) {
        for (T el : array) {
            if (value.equals(el)) {
                return true;
            }
        }
        return false;
    }
}
