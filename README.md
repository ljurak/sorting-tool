# Sorting tool

Console app enabling sorting different types of data (e.g. numbers, words, lines of text).
It offers sorting in natural order or sorting by count. Response may be printed on a console
or written to a file.

| Launch parameters | Permitted values                              |
|-------------------|-----------------------------------------------|
| -dataType         | long, line, word (optional, default: word)    |
| -sortingType      | natural, byCount (optional, default: natural) |
| -inputFile        | path to a file (optional)                     |
| -outputFile       | path to a file (optional)                     |

## How to start

```bash
git clone https://github.com/ljurak/sorting-tool.git
cd sorting-tool
mvn clean package
cd target
java -jar sorting-tool.jar [-dataType long -sortingType natural -inputFile in.txt -outputFile out.txt]
```