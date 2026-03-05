package pl.lab.factory;

// Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
// Wzorzec Singleton 4 (Enum)
// Jedyna instancja loggera (INSTANCE) jest tworzona podczas ładowania klasy.
// Zapewnia bezpieczeństwo wątkowe bez dodatkowej synchronizacji.
public enum SystemLogger {
    INSTANCE;

    private static final int CONTEXT_WIDTH = 20;
    private static final int TYPE_WIDTH = 10;

    private String padRight(String text, int length) {
        if (text.length() >= length) return text;
        return text + " ".repeat(length - text.length());
    }

    public void log(String context, String message) {
        String c = padRight(context, CONTEXT_WIDTH);
        String t = padRight("", TYPE_WIDTH);
        System.out.println(c + t + message);
    }

    public void logInfo(String context, String message) {
        String c = padRight(context, CONTEXT_WIDTH);
        String t = padRight("[INFO]", TYPE_WIDTH);
        System.out.println(c + t + message);
    }

    public void logError(String context, String message) {
        String c = padRight(context, CONTEXT_WIDTH);
        String t = padRight("[ERROR]", TYPE_WIDTH);
        System.out.println(c + t + message);
    }
}
// Koniec, Tydzień 2
// Wzorzec Singleton 4