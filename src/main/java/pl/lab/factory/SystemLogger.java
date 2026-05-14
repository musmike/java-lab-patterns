package pl.lab.factory;

// Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
// Wzorzec Singleton 4 (Enum)
// Jedyna instancja loggera (INSTANCE) jest tworzona podczas ładowania klasy.
// Zapewnia bezpieczeństwo wątkowe bez dodatkowej synchronizacji.
public enum SystemLogger {
    INSTANCE;

    private static final int CONTEXT_WIDTH = 40;
    private static final int TYPE_WIDTH = 10;
    private static final String ELLIPSIS = "...";

    private String padRight(String text, int length) {
        if (text.length() > length) {
            int ellipsisLength = ELLIPSIS.length();
            if (length > ellipsisLength) {
                return text.substring(0, length - ellipsisLength) + ELLIPSIS;
            } else {
                return text.substring(0, length);
            }
        }
        return text + " ".repeat(length - text.length());
    }

    public void log(String context, String message) {
        printLogLine(context, "", message);
    }

    public void logInfo(String context, String message) {
        printLogLine(context, "[INFO]", message);
    }

    public void logError(String context, String message) {
        printLogLine(context, "[ERROR]", message);
    }

    private void printLogLine(String context, String prefix, String message) {
        String paddedContext = padRight(context, CONTEXT_WIDTH);
        String paddedType = padRight(prefix, TYPE_WIDTH);
        System.out.println(paddedType + paddedContext + "\t" + message);
    }
}
// Koniec, Tydzień 2
// Wzorzec Singleton 4