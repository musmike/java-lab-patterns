package pl.lab.factory;

public enum SystemLogger {
    INSTANCE;

    public void log(String context, String message) {
        System.out.println("[" + context + "] " + message);
    }

    public void logInfo(String context, String message) {
        System.out.println("[" + context + "][INFO] " + message);
    }

    public void logError(String context, String message) {
        System.out.println("[" + context + "][ERROR] " + message);
    }
}
