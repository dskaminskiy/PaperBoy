package deniskaminskiy.paperboy.data.api.interceptors.utils;

import java.io.Writer;

public class AggregateTranslator extends CharSequenceTranslator {

    private final CharSequenceTranslator[] translators;

    public AggregateTranslator(final CharSequenceTranslator... translators) {
        this.translators = clone(translators);
    }

    public static <T> T[] clone(final T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    @Override
    public int translate(final CharSequence input, final int index, final Writer out) {
        for (final CharSequenceTranslator translator : translators) {
            final int consumed = translator.translate(input, index, out);

            if(consumed != 0) {
                return consumed;
            }
        }

        return 0;
    }

}
