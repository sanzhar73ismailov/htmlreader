package com.peer39.htmlreader;

import com.peer39.htmlreader.pkg.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunnerTest {

    @Test
    @DisplayName("Remove JavaScript code, simple asserts")
    void removeJsCodeSimpleAsserts() {
        assertEquals("b", Util.removeJsCode("<script>var a = 't';</script>b"));
        assertEquals("a", Util.removeJsCode("a<script>var a = 't';</script>"));
        assertEquals("ab", Util.removeJsCode("a<script>var a = 't';</script>b<script>var b = 't';</script>"));
        assertEquals("abc", Util.removeJsCode("a<script type=\"text/javascript\">var a = 't';</script>b<script>var b = 't';</script>c"));
    }

    @Test
    @DisplayName("Remove JavaScript code")
    void removeJsCode() {
        String html = "<html>\n" +
                      "<head>\n" +
                      "<title>Some title</title>\n" +
                      "<script>\n" +
                      "    var x = 123;\n" +
                      "</script>\n" +
                      "<script type=\"text/javascript\">\n" +
                      "    var a = '<<<';\n" +
                      "</script>\n" +
                      "</head>\n" +
                      "<body><div>Some div block</div>\n" +
                      "<script type=\"text/javascript\">\n" +
                      "    var b = '<<<';\n" +
                      "</script>\n" +
                      "</body>\n" +
                      "</html>";
        String expected = "<html>\n" +
                          "<head>\n" +
                          "<title>Some title</title>\n" +
                          "\n" +
                          "\n" +
                          "</head>\n" +
                          "<body><div>Some div block</div>\n" +
                          "\n" +
                          "</body>\n" +
                          "</html>";
        assertEquals(expected, Util.removeJsCode(html));
    }

    @DisplayName("Remove CSS code")
    @Test
    void removeCssCode() {
        String html = "<html>\n" +
                      "<head>\n" +
                      "<title>Some title</title>\n" +
                      "<style>\n" +
                      ".tab-bar .menu-icon span::after {\n" +
                      "   border-top: 1px solid #fff;\n" +
                      "  }\n" +
                      "</style>\n" +
                      "</head>\n" +
                      "<body><div>Some div block</div>\n" +
                      "</body>\n" +
                      "</html>";
        String expected = "<html>\n" +
                          "<head>\n" +
                          "<title>Some title</title>\n" +
                          "\n" +
                          "</head>\n" +
                          "<body><div>Some div block</div>\n" +
                          "</body>\n" +
                          "</html>";
        assertEquals(expected, Util.removeCssCode(html));
    }

    @DisplayName("Remove html comments")
    @Test
    void removeHtmlComments() {
        String html = "<html>\n" +
                      "<head>\n" +
                      "<title>Some title</title>\n" +
                      "<!-- comment1 <<< >>> <! <!- -> -->\n" +
                      "</head>\n" +
                      "<body><div>Some div block</div>\n" +
                      "<!-- comment2 -->\n" +
                      "</body>\n" +
                      "</html>";
        String expected = "<html>\n" +
                          "<head>\n" +
                          "<title>Some title</title>\n" +
                          "\n" +
                          "</head>\n" +
                          "<body><div>Some div block</div>\n" +
                          "\n" +
                          "</body>\n" +
                          "</html>";
        assertEquals(expected, Util.removeHtmlComments(html));
    }

    @DisplayName("Remove all html tags code")
    @Test
    void removeAllHtmlTagsCode() {
        String html = "<html>\n" +
                      "<head>\n" +
                      "<title>Some title</title>\n" +
                      "</head>\n" +
                      "<body>\n" +
                      "<div>Some div block1</div>\n" +
                      "<div>Some div block2</div>\n" +
                      "</body>\n" +
                      "<html>";
        String expected = "Some title\n\n\n" +
                          "Some div block1\n" +
                          "Some div block2";
        assertEquals(expected, Util.removeAllHtmlTagsCode(html));
    }

    @DisplayName("Replace block elements by new line")
    @Test
    void replaceBlockElementsByNewLine() {
        String html = "<html>" +
                      "<head>" +
                      "<title>Some title</title>" +
                      "</head>" +
                      "<body><div>Some div block1</div>" +
                      "<body><div>Some div block2</div>" +
                      "</body>" +
                      "</html>";
        String expected = "<html>" +
                          "<head>" +
                          "\nSome title" +
                          "</head>" +
                          "<body>\nSome div block1" +
                          "<body>\nSome div block2" +
                          "</body>" +
                          "</html>";
        assertEquals(expected, Util.replaceBlockElementsByNewLine(html));
    }

    @DisplayName("Replace several new lines with only one")
    @Test
    void replaceSeveralNewLinesWithOnlyOne() {
        String text = "\n11\n22\n\n33\n\n\n4\n";
        String expected = "11\n22\n33\n4";
        assertEquals(expected, Util.replaceSeveralNewLinesWithOnlyOne(text));
    }

    @DisplayName("Extract text")
    @Test
    void extractText() {
        String html = "<html>" +
                      "<head>" +
                      "<title>Some title</title>" +
                      "<style>\n" +
                      ".tab-bar .menu-icon span::after {\n" +
                      "   border-top: 1px solid #fff;\n" +
                      "  }\n" +
                      "</style>\n" +
                      "<script>\n" +
                      "    var x = 123;\n" +
                      "</script>\n" +
                      "<script type=\"text/javascript\">\n" +
                      "    var a = '<<<';\n" +
                      "</script>\n" +
                      "<!-- comment2 -->\n" +
                      "</head>" +
                      "<body>" +
                      "<div>Some div block1</div>" +
                      "<!-- comment2 -->\n" +
                      "<div>Some div block2</div>" +
                      "<script type=\"text/javascript\">\n" +
                      "    var b = '<<<';\n" +
                      "</script>\n" +
                      "</body>" +
                      "<!-- comment2 -->\n" +
                      "</html>";
        String expected = "Some title\n" +
                          "Some div block1\n" +
                          "Some div block2";
        assertEquals(expected, Util.extractText(html));
    }

    @DisplayName("Replace two and more spaces by one")
    @Test
    void replaceTwoAndMoreSpacesByOne() {
        assertEquals("1 2 3 4", Util.replaceTwoAndMoreSpacesByOne("  1 2    3   4  "));
    }

    @DisplayName("Trim white space from the beginning and end of each line")
    @Test
    void trimWhiteSpaceFromTheBeginningAndEndOfEachLine() {
        assertEquals("1\n2\n3\n4\n5", Util.trimWhiteSpaceFromTheBeginningAndEndOfEachLine("  1\n  2   \n3  \n4\n  5 "));
    }

    @DisplayName("Unescape html")
    @Test
    void unescapeHtml() {
        String html = "Here is some &quot;Text&quot; thatI'd like to be &quot;escaped&quot; for Java.I'll try a couple special characters here";
        String expected = "Here is some \"Text\" thatI'd like to be \"escaped\" for Java.I'll try a couple special characters here";
        assertEquals(expected, Util.unescapeHtml(html));
    }
}