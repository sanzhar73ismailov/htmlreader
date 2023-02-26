package com.peer39.htmlreader.pkg;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

    public static void main(String[] args) {
        String text = readFile("c:/temp/peer39/radiosport.html");
        text = extractText(text);
        System.out.println(text);
    }

    public static String extractText(String html) {
        html = removeJsCode(html);
        html = removeCssCode(html);
        html = removeHtmlComments(html);
        html = replaceBlockElementsByNewLine(html);
        html = removeAllHtmlTagsCode(html);
        html = trimWhiteSpaceFromTheBeginningAndEndOfEachLine(html);
        html = replaceSeveralNewLinesWithOnlyOne(html);
        html = replaceTwoAndMoreSpacesByOne(html);
        html = unescapeHtml(html);
        return html;
    }

    public static String trimWhiteSpaceFromTheBeginningAndEndOfEachLine(String text) {
        return Arrays.stream(text.split("\n"))
                .map(String::trim)
                .collect(Collectors.joining("\n"));
    }

    public static String replaceSeveralNewLinesWithOnlyOne(String html) {
        return Pattern.compile("\n{2,}").matcher(html).replaceAll("\n").trim();
    }

    public static String replaceTwoAndMoreSpacesByOne(String html) {
        return Pattern.compile(" +").matcher(html).replaceAll(" ").trim();
    }


    public static String unescapeHtml(String html) {
        return StringEscapeUtils.unescapeHtml4(html);
    }

    public static String replaceBlockElementsByNewLine(String html) {
        String patternForNewLines = "<(" + String.join("|", HtmlElements.BLOCK_ELEMENTS) + ")>";
        String patternForRemoving = "</(" + String.join("|", HtmlElements.BLOCK_ELEMENTS) + ")>";
        html = Pattern.compile(patternForNewLines, Pattern.CASE_INSENSITIVE).matcher(html).replaceAll("\n");
        html = Pattern.compile(patternForRemoving, Pattern.CASE_INSENSITIVE).matcher(html).replaceAll("");
        return html.trim();
    }

    public static String removeAllHtmlTagsCode(String html) {
        return Pattern.compile("<(?:\"[^\"]*\"['\"]*|'[^']*'['\"]*|[^'\">])+>")
                .matcher(html)
                .replaceAll("").trim();
    }

    public static String removeHtmlComments(String html) {
        return Pattern.compile("<!--.+?-->")
                .matcher(html)
                .replaceAll("").trim();
    }

    public static String removeCssCode(String str) {
        return removeSomeCode(str, "style");
    }

    public static String removeJsCode(String str) {
        return removeSomeCode(str, "script");
    }

    private static String removeSomeCode(String text, String tag) {
        //"<script[\\s>][\\S\\s]*?</script>"
        String patternStr = "<" + tag + "[\\s>][\\S\\s]*?</" + tag + ">";
        return Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE)
                .matcher(text)
                .replaceAll("");
    }

    public static String readFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            new RuntimeException(e);
        }
        return contentBuilder.toString();
    }
}
