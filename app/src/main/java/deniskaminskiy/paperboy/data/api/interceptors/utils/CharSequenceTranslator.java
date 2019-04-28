package deniskaminskiy.paperboy.data.api.interceptors.utils;

import java.io.StringWriter;
import java.io.Writer;

public abstract class CharSequenceTranslator {

    public abstract int translate(CharSequence input, int index, Writer out);

    public final String translate(final CharSequence input) {
        if (input == null) {
            return null;
        }
        try {
            final StringWriter writer = new StringWriter(input.length() * 2);
            translate(input, writer);
            return writer.toString();
        } catch (Exception ioe) {
            return "";
        }
    }

    public final void translate(final CharSequence input, final Writer out) {
        if (out == null) {
            return;
        }
        if (input == null) {
            return;
        }
        int pos = 0;
        final int len = input.length();
        try {
            while (pos < len) {
                final int consumed = translate(input, pos, out);
                if (consumed == 0) {
                    char c1 = input.charAt(pos);
                    out.write(c1);
                    pos++;
                    if (Character.isHighSurrogate(c1) && pos < len) {
                        char c2 = input.charAt(pos);
                        if (Character.isLowSurrogate(c2)) {
                            out.write(c2);
                            pos++;
                        }
                    }
                    continue;
                }

                for (int pt = 0; pt < consumed; pt++) {
                    pos += Character.charCount(Character.codePointAt(input, pos));
                }
            }
        } catch (Exception e) {
            //.
        }
    }

    public final CharSequenceTranslator with(final CharSequenceTranslator... translators) {
        final CharSequenceTranslator[] newArray = new CharSequenceTranslator[translators.length + 1];
        newArray[0] = this;
        System.arraycopy(translators, 0, newArray, 1, translators.length);
        return new AggregateTranslator(newArray);
    }

}

