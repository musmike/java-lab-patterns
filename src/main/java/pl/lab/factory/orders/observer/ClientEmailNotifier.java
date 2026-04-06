package pl.lab.factory.orders.observer;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Observer 1 (Konkretny obserwator)
// Symuluje wysyłanie powiadomień e-mail do klienta po każdej zmianie statusu.
public class ClientEmailNotifier implements OrderObserver {
    private final String clientEmail;

    public ClientEmailNotifier(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    @Override
    public void update(ProductionOrder order) {
        SystemLogger.INSTANCE.logInfo("EMAIL NOTIFIER",
                "To: [" + clientEmail + "] -> Dear Client, your order "
                        + order.getId() + " is now in status: " + order.getStatus());
    }
}
// Koniec, Tydzień 6
// Wzorzec Observer 1
