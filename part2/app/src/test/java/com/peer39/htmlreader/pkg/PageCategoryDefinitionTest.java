package com.peer39.htmlreader.pkg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PageCategoryDefinitionTest {
    List<Category> categories;

    @BeforeEach
    void setUp() {
        categories = Arrays.asList(
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
                        .build(),
                Category.builder()
                        .name("Alcohol")
                        .keywords(Arrays.asList(
                                "wine", "beer", "vodka", "whiskey", "sparkling wine", "armagnac"
                        ))
                        .build()
        );
    }

    @Test
    @DisplayName("test if page has error")
    void checkIfPageHasError() {
        Result result = Result.builder()
                .url("http://hello.com")
                .success(false)
                .text("")
                .errorMessage("HTTP/1.1 403 Forbidden")
                .responseStatus(403)
                .build();
        final Page page = PageCategoryDefinition.categoryDefine(result, categories);
        assertEquals("http://hello.com", page.getUrl().toLowerCase());
        assertTrue(page.getCategories().isEmpty());
    }

    @Test
    @DisplayName("test if no categories are found")
    void checkIfNoCategoriesFound() {
        Result result = Result.builder()
                .url("http://hello.com")
                .success(true)
                .text("hello! We are not opening")
                .build();
        final Page page = PageCategoryDefinition.categoryDefine(result, categories);
        assertEquals("http://hello.com", page.getUrl().toLowerCase());
        assertTrue(page.getCategories().isEmpty());
    }


    @Test
    @DisplayName("test if one category is found")
    void checkIfOneCategoryFound() {
        Result result = Result.builder()
                .url("http://Alcohol.com")
                .success(true)
                .text("White Italian Wines. Red Italian Wines")
                .build();
        final Page page = PageCategoryDefinition.categoryDefine(result, categories);
        assertEquals("http://alcohol.com", page.getUrl().toLowerCase());
        assertEquals(1, page.getCategories().size());
        assertEquals("Alcohol", page.getCategories().get(0).getName());
    }

    @Test
    @DisplayName("test if two categories are found")
    void checkIfTwoCategoriesFound() {
        Result result = Result.builder()
                .url("http://SUPERMARKET.COM")
                .success(true)
                .text("Italian Wines, Basketball balls")
                .build();
        final Page page = PageCategoryDefinition.categoryDefine(result, categories);
        assertEquals("http://supermarket.com", page.getUrl().toLowerCase());
        assertEquals(2, page.getCategories().size());
        final List<String> categoryNames = page.getCategories().stream().map(Category::getName).collect(Collectors.toList());
        assertTrue(categoryNames.contains("Alcohol"));
        assertTrue(categoryNames.contains("Basketball"));
    }
}