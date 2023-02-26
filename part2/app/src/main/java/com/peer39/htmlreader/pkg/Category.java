package com.peer39.htmlreader.pkg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    private String name;
    private List<String> keywords;

    /**
     * To reduce search time, the keywords in the categories are sorted by length.
     * Search for a short word is faster than a long one.
     */
    public void sortKeyWordsByLength() {
        this.setKeywords(this.keywords == null ? null :
                this.keywords.stream()
                        .sorted(Comparator.comparingInt(String::length))
                        .collect(Collectors.toList())
        );
    }

}
