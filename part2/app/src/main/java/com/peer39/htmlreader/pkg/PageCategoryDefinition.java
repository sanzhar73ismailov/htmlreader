package com.peer39.htmlreader.pkg;

import java.util.ArrayList;
import java.util.List;

public class PageCategoryDefinition {

    public static Page categoryDefine(Result result, List<Category> categories) {
        Page page = Page.builder()
                .url(result.getUrl())
                .categories(new ArrayList<>())
                .build();
        if (!result.isSuccess())
            return page;
        String textOfPage = result.getText().toLowerCase();
        for (Category category : categories) {
            category.sortKeyWordsByLength();
            for (String keyword : category.getKeywords()) {
                if (textOfPage.contains(keyword.toLowerCase())) {
                    page.getCategories().add(category);
                    break;
                }
            }
        }
        return page;
    }
}
