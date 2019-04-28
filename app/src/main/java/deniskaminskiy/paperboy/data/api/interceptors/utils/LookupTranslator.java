package deniskaminskiy.paperboy.data.api.interceptors.utils;

import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;

public class LookupTranslator extends CharSequenceTranslator {

    private final HashMap<String, String> lookupMap;
    private final HashSet<Character> prefixSet;
    private final int shortest;
    private final int longest;

    public LookupTranslator(final CharSequence[]... lookup) {
        lookupMap = new HashMap<>();
        prefixSet = new HashSet<>();
        int _shortest = Integer.MAX_VALUE;
        int _longest = 0;
        if (lookup != null) {
            for (final CharSequence[] seq : lookup) {
                this.lookupMap.put(seq[0].toString(), seq[1].toString());
                this.prefixSet.add(seq[0].charAt(0));
                final int sz = seq[0].length();
                if (sz < _shortest) {
                    _shortest = sz;
                }
                if (sz > _longest) {
                    _longest = sz;
                }
            }
        }
        shortest = _shortest;
        longest = _longest;
    }

    @Override
    public int translate(final CharSequence input, final int index, final Writer out) {
        try {
            if (prefixSet.contains(input.charAt(index))) {
                int max = longest;
                if (index + longest > input.length()) {
                    max = input.length() - index;
                }

                for (int i = max; i >= shortest; i--) {
                    final CharSequence subSeq = input.subSequence(index, index + i);
                    final String result = lookupMap.get(subSeq.toString());

                    if (result != null) {
                        out.write(result);
                        return i;
                    }
                }
            }
        } catch (Exception e) {
            //
        }

        return 0;
    }

}
