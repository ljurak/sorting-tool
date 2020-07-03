package sorting;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Arguments arguments = new Arguments();

    public static void main(String[] args) {
        arguments.processArguments(args);

        if (!arguments.isValidArguments()) {
            return;
        }

        try {
            String response = null;
            switch (arguments.getDataType()) {
                case "long":
                    response = processLongs();
                    break;
                case "line":
                    response = processLines();
                    break;
                case "word":
                    response = processWords();
                    break;
            }

            if (arguments.getOutputFile() == null) {
                System.out.println(response);
            } else {
                saveResponseToFile(arguments.getOutputFile(), response);
            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    private static String processLongs() throws IOException {
        List<Long> numbers = new ArrayList<>();
        for (String value : readUserData()) {
            try {
                numbers.add(Long.parseLong(value));
            } catch (NumberFormatException e) {
                System.out.println("\"" + value + "\" isn't a long. It's skipped.");
            }
        }

        String title = "Total numbers: " + numbers.size() + "." + System.lineSeparator();
        return processSorting(numbers, title, false);
    }

    private static String processLines() throws IOException {
        List<String> lines = readUserData();

        String title = "Total lines: " + lines.size() + "." + System.lineSeparator();
        return processSorting(lines, title, true);
    }

    private static String processWords() throws IOException {
        List<String> words = readUserData();

        String title = "Total words: " + words.size() + "." + System.lineSeparator();
        return processSorting(words, title, false);
    }

    private static <T extends Comparable<? super T>> String processSorting(List<T> data, String title, boolean printMultiline) {
        StringBuilder response = new StringBuilder(title);
        String sortingType = arguments.getSortingType();

        if (data.size() > 0) {
            if ("natural".equals(sortingType)) {
                Collections.sort(data);
                response.append("Sorted data: ");
                if (printMultiline) {
                    response.append(System.lineSeparator());
                }
                for (T elem : data) {
                    response.append(elem)
                            .append(printMultiline ? System.lineSeparator() : " ");
                }
            } else if ("byCount".equals(sortingType)) {
                Map<T, Integer> sorted = sortByCount(data);
                sorted.entrySet().stream()
                        .sorted((first, second) -> first.getValue().equals(second.getValue())
                                ? first.getKey().compareTo(second.getKey())
                                : first.getValue() - second.getValue())
                        .forEach(entry -> {
                            long percentage = Math.round(((double) entry.getValue() / data.size()) * 100);
                            response.append(entry.getKey())
                                    .append(": ")
                                    .append(entry.getValue())
                                    .append(" time(s), ")
                                    .append(percentage)
                                    .append("%).")
                                    .append(System.lineSeparator());
                        });
            }
        }
        return response.toString();
    }

    private static <T extends Comparable<? super T>> Map<T, Integer> sortByCount(List<T> data) {
        Map<T, Integer> result = new HashMap<>();
        data.forEach(el -> result.merge(el, 1, Integer::sum));
        return result;
    }

    private static List<String> readUserData() throws IOException {
        Scanner scanner = arguments.getInputFile() == null
                ? new Scanner(System.in)
                : new Scanner(Paths.get(arguments.getInputFile()), StandardCharsets.UTF_8);

        List<String> data = new ArrayList<>();
        switch (arguments.getDataType()) {
            case "long":
            case "word":
                while (scanner.hasNext()) {
                    data.add(scanner.next());
                }
                break;
            case "line":
                while (scanner.hasNextLine()) {
                    data.add(scanner.nextLine());
                }
                break;
        }

        scanner.close();
        return data;
    }

    private static void saveResponseToFile(String filename, String text) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(text);
        }
    }
}
