package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * create a new stream catcher for err and out stream
 */
public class StreamCatcher {
    private static final String LINE_INDICATOR = "\n";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private ByteArrayOutputStream currentContent = null;

    /**
     * Acquire the streams
     */
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * Release the streams
     */
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    /**
     * Check if the stream is equals to a string. A stream need to be selected,
     * otherwise, the check will be dealed on both streams
     *
     * e.g:
     *
     * <pre>
     * streamCatcher.out().Equals("a string")
     * streamCatcher.err().Equals("a string")
     * streamCatcher.all().Equals("a string")
     * //the same as all() if first call
     * streamCatcher.Equals("a string")
     * </pre>
     *
     *
     * Assertion Error if the condition is not validated
     *
     * The buffer will be Automatically cleaned on the corresponding stream after
     * the process
     *
     * @param string
     */
    public void equals(String string) {
        String expected = createInputStream(string);
        String content = currentContent.toString();
        assertEquals(expected, content);
        clean();
    }

    /**
     * Check if the stream is not equals to a string. A stream need to be selected,
     * otherwise, the check will be dealed on both streams
     *
     * e.g:
     *
     * <pre>
     * streamCatcher.out().notEquals("a string")
     * streamCatcher.err().notEquals("a string")
     * streamCatcher.all().notEquals("a string")
     * //the same as all() if first call
     * streamCatcher.notEquals("a string")
     * </pre>
     *
     * Assertion Error if the condition is not validated
     *
     * The buffer will be Automatically cleaned on the corresponding stream after
     * the process
     *
     * @param string
     */
    public void notEquals(String string) {
        String expected = createInputStream(string);
        String content = currentContent.toString();
        assertNotEquals(expected, content);
        clean();
    }

    /**
     * Check if the stream contains to a string. A stream need to be selected,
     * otherwise, the check will be dealed on both streams
     *
     * e.g:
     *
     * <pre>
     * streamCatcher.out().contains("a string").clean()
     * streamCatcher.out().contains("a string").contains("an other string").clean()
     * streamCatcher.err().contains("a string").clean()
     * streamCatcher.all().contains("a string").clean()
     * //the same as all() if first call
     * streamCatcher.contains("a string").clean()
     * </pre>
     *
     * Assertion Error if the condition is not validated
     *
     * The buffer will not be cleaned on the corresponding stream after the process
     * You need to do it manually ending with <code>clean()</code>
     *
     * @param string
     */
    public StreamCatcher contains(String string) {
        String content = extractFromStream();
        if (!content.contains(string)) {
            fail(message("contain", string, content));
        }
        return this;
    }

    /**
     *
     * Check if the stream not contains to a string. A stream need to be selected,
     * otherwise, the check will be dealed on both streams
     *
     * e.g:
     *
     * <pre>
     * streamCatcher.out().notContains("a string").clean()
     * streamCatcher.out().notContains("a string").notContains("an other string").clean()
     * streamCatcher.err().notContains("a string").clean()
     * streamCatcher.all().notContains("a string").clean()
     * //the same as all() if first call
     * streamCatcher.notContains("a string").clean()
     * </pre>
     *
     * The buffer will not be cleaned on the corresponding stream after the process
     * You need to do it manually ending with <code>clean()</code>
     *
     * Assertion Error if the condition is not validated
     *
     * @param string
     */
    public StreamCatcher notContains(String string) {
        String content = extractFromStream();
        if (content.contains(string)) {
            fail(message("not contain", string, content));
        }
        return this;
    }

    /**
     * select the out stream
     *
     * @return
     */
    public StreamCatcher out() {
        this.currentContent = outContent;
        return this;
    }

    /**
     * select the err stream
     *
     * @return
     */
    public StreamCatcher err() {
        this.currentContent = errContent;
        return this;
    }

    /**
     * select all streams (out and err)
     *
     * @return
     */
    public StreamCatcher all() {
        this.currentContent = null;
        return this;
    }

    /**
     * clean the current stream (or all if not set)
     */
    public void clean() {
        if (this.currentContent != null) {
            this.currentContent.reset();
            this.currentContent = null;
        } else {
            this.outContent.reset();
            this.errContent.reset();
        }
    }

    private String message(String rule, String expected, String actual) {
        return "Expected it to " + rule + " \"" + expected + "\" but was \"" + actual + "\"";
    }

    private String extractFromStream() {
        StringBuilder content = new StringBuilder();
        if (currentContent == null) {
            content.append(errContent.toString());
            content.append(outContent.toString());
        } else {
            content.append(currentContent.toString());
        }
        return content.toString();
    }

    private String createInputStream(String string) {
        StringWriter expectedStringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(expectedStringWriter);
        String[] lines = string.split(LINE_INDICATOR);
        for (String l : lines) {
            printWriter.println(l);
        }
        printWriter.close();
        String expected = expectedStringWriter.toString();
        return expected;
    }

}
