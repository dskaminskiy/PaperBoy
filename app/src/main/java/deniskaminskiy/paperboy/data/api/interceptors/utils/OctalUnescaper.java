package deniskaminskiy.paperboy.data.api.interceptors.utils;

import java.io.Writer;

public class OctalUnescaper extends CharSequenceTranslator {

    @Override
    public int translate(final CharSequence input, final int index, final Writer out) {
        final int remaining = input.length() - index - 1; // how many characters left, ignoring the first \
        final StringBuilder builder = new StringBuilder();

        try {
            if(input.charAt(index) == '\\' && remaining > 0 && isOctalDigit(input.charAt(index + 1)) ) {
                final int next = index + 1;
                final int next2 = index + 2;
                final int next3 = index + 3;

                // we know this is good as we checked it in the if block above
                builder.append(input.charAt(next));

                if(remaining > 1 && isOctalDigit(input.charAt(next2))) {
                    builder.append(input.charAt(next2));
                    if(remaining > 2 && isZeroToThree(input.charAt(next)) && isOctalDigit(input.charAt(next3))) {
                        builder.append(input.charAt(next3));
                    }
                }

                out.write( Integer.parseInt(builder.toString(), 8) );
                return 1 + builder.length();
            }
        } catch (Exception e) {
            //
        }

        return 0;
    }

    private boolean isOctalDigit(final char ch) {
        return ch >= '0' && ch <= '7';
    }

    private boolean isZeroToThree(final char ch) {
        return ch >= '0' && ch <= '3';
    }

}
