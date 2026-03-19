package seedu.traveltrio;

import java.util.HashMap;

public class Parser {

    public static HashMap<String, String> parseArgs(String args, String... prefixes) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < prefixes.length; i++) {
            String currentPrefix = prefixes[i];
            int start = args.indexOf(currentPrefix);
            if (start == -1) {
                continue;
            }

            start += currentPrefix.length();
            int end = args.length();

            // Find the start of the next prefix to know where to stop
            for (String nextPrefix : prefixes) {
                int nextStart = args.indexOf(nextPrefix, start);
                if (nextStart != -1 && nextStart < end) {
                    end = nextStart;
                }
            }
            map.put(currentPrefix, args.substring(start, end).trim());
        }
        return map;
    }
}
