package com.peer39.htmlreader.pkg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page {
    String url;
    List<Category> categories;

    public String toString() {
        return String.format("page url: %s, categories: %s", url,
                categories.stream().
                        map(Category::getName)
                        .collect(Collectors.toList()));
    }
}
