/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.peer39.htmlreader;

import com.peer39.htmlreader.pkg.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Runner {
    private static final List<String> URLS = Arrays.asList(
            "http://www.starwars.com",
            "https://www.imdb.com/find?q=star+wars&ref_=nv_sr_sm",
            "https://edition.cnn.com/sport"
    );

    public static void main(String[] args) {
        final List<Category> categories = initializeModel();
        run(categories, URLS);
    }

    /**
     * implementation complexity N * M * K
     * assuming
     * N - the text length
     * M - number of categories
     * K - max keyword length.
     * @param categories
     * @param urls
     */
    public static void run(List<Category> categories, List<String> urls) {
        PageReader pageReader = new PageReader();
        final List<Result> results = pageReader.readPages(URLS);
        for (int i = 0; i < results.size(); i++) {
            final Result result = results.get(i);
//            System.out.printf("result N%s of %s, url: %s, success: %s, error message: %s, length of text: %s%n",
//                    i + 1,
//                    results.size(),
//                    result.getUrl(),
//                    result.isSuccess(),
//                    result.getErrorMessage(),
//                    result.getText().length());
            final Page page = PageCategoryDefinition.categoryDefine(result, categories);
            System.out.println(page);
        }
    }

    public static List<Category> initializeModel() {
        List<Category> categories = Arrays.asList(
                Category.builder()
                        .name("Star Wars")
                        .keywords(Arrays.asList(
                                "star war", "starwars", "starwar", "r2d2", "may the force be with you"
                        ))
                        .build(),
                Category.builder()
                        .name("Basketball")
                        .keywords(Arrays.asList(
                                "basketball", "nba", "ncaa", "lebron james", "john stokton", "anthony davis"
                        ))
                        .build()
        );
        return categories;
    }


}
