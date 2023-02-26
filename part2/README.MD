## to run project in linux
cd /path/htmlreader/part2
./gradlew run
## to run project in Windows
cd c:\path\htmlreader\part2
.\gradlew.bat run

## com.peer39.htmlreader.Runner.run method
     * implementation complexity N * M * K 
     * N - the text length
     * M - number of categories
     * K - max keyword length.

## To reduce search time
To reduce search time, the keywords in the categories are sorted by length.
Search for a short word is faster than a long one.
