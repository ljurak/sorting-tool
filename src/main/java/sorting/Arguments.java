package sorting;

import java.util.Set;

public class Arguments {

    private final Set<String> validArgumentNames = Set.of("-dataType", "-sortingType");

    private final Set<String> validDataTypes = Set.of("long", "line", "word");

    private final Set<String> validSortingTypes = Set.of("natural", "byCount");

    private boolean validArguments = true;

    private String dataType = "word";

    private String sortingType = "natural";

    public String getDataType() {
        return dataType;
    }

    public String getSortingType() {
        return sortingType;
    }

    public boolean isValidArguments() {
        return validArguments;
    }

    public void processArguments(String[] arguments) {
        String currentArgumentName = null;
        boolean isArgumentValueExpected = false;

        for (String argument : arguments) {
            if (isArgumentValueExpected) {
                if (argument.startsWith("-")) {
                    showMissingArgument(currentArgumentName);
                    validArguments = false;
                    return;
                } else {
                    setArgumentValue(currentArgumentName, argument);
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

    private boolean validateArgumentName(String name) {
        return validArgumentNames.contains(name);
    }

    private boolean validateArgumentValue(String name, String value) {
        if ("-dataType".equals(name)) {
            return validDataTypes.contains(value);
        } else if ("-sortingType".equals(name)) {
            return validSortingTypes.contains(value);
        }
        return false;
    }

    private void setArgumentValue(String name, String value) {
        if (validateArgumentValue(name, value)) {
            if ("-dataType".equals(name)) {
                dataType = value;
            } else if ("-sortingType".equals(name)) {
                sortingType = value;
            }
        }
    }

    private void showMissingArgument(String argument) {
        if ("-dataType".equals(argument)) {
            System.out.println("No data type defined!");
        } else if ("-sortingType".equals(argument)) {
            System.out.println("No sorting type defined!");
        }
    }
}
