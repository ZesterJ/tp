package seedu.traveltrio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.HashMap;

public class ParserTest {
    @Test
    public void parseArgs_validInput_success() {
        String input = "n/Seoul s/2026-12-01 e/2026-12-10";
        HashMap<String, String> result = Parser.parseArgs(input, "n/", "s/", "e/");

        assertEquals("Seoul", result.get("n/"));
        assertEquals("2026-12-01", result.get("s/"));
        assertEquals("2026-12-10", result.get("e/"));
    }

    @Test
    public void parseArgs_missingPrefix_returnsNull() {
        String input = "n/Seoul s/2026-12-01";
        HashMap<String, String> result = Parser.parseArgs(input, "n/", "s/", "e/");
        assertEquals(null, result.get("e/"), "Should be null if prefix is missing.");
    }
}
