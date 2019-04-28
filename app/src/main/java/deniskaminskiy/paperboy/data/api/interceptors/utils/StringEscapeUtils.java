package deniskaminskiy.paperboy.data.api.interceptors.utils;

public final class StringEscapeUtils {

    private static String[][] invert(final String[][] array) {
        final String[][] newArray = new String[array.length][2];

        for (int i = 0; i < array.length; i++) {
            newArray[i][0] = array[i][1];
            newArray[i][1] = array[i][0];
        }

        return newArray;
    }

    private static final CharSequenceTranslator UNESCAPE_JAVA =
            new AggregateTranslator(
                    new OctalUnescaper(),
                    new UnicodeUnescaper(),
                    new LookupTranslator(invert(new String[][] {
                            {"\b", "\\b"},
                            {"\n", "\\n"},
                            {"\t", "\\t"},
                            {"\f", "\\f"},
                            {"\r", "\\r"}
                    })),
                    new LookupTranslator(
                            new String[][] {
                                    {"\\\\", "\\"},
                                    {"\\\"", "\""},
                                    {"\\'", "'"},
                                    {"\\", ""}
                            })
            );

    public static String unescapeJava(final String input) {
        return UNESCAPE_JAVA.translate(input);
    }

}
