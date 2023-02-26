/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.peer39.htmlreader;

import com.peer39.htmlreader.pkg.PageReader;
import com.peer39.htmlreader.pkg.Result;

import java.util.Arrays;
import java.util.List;

public class Part1 {
    private static final List<String> URLS = Arrays.asList(
            "http://www.msn.com/en-nz/travel/tripideas/70-of-the-planets-most-breathtaking-sights/ss-AAIUpDp",
            "https://www.radiosport.co.nz/sport-news/rugby/accident-or-one-last-dig-eddie-jones-reveals-hansens-next-job/",
            "https://www.glamour.de/frisuren/frisurenberatung/haarschnitte",
            "https://www.bbc.com",
            "https://www3.forbes.com/business/2020-upcoming-hottest-new-vehicles/13/?nowelcome",
            "https://www.tvblog.it/post/1681999/valerio-fabrizio-salvatori-gli-inseparabili-chi-sono-pechino-express-2020",
            "http://edition.cnn.com/"
    );

    public static void main(String[] args) {
        PageReader pageReader = new PageReader();
        final List<Result> results = pageReader.readPages(URLS);
        for (int i = 0; i < results.size(); i++) {
            final Result result = results.get(i);
            String urlText = result.isSuccess() ? result.getText() : result.getErrorMessage();
            System.out.printf("url: N %s of %s) %s %nurl text:%n%s%n%n", i + 1, results.size(), result.getUrl(), urlText);
        }
    }
}
