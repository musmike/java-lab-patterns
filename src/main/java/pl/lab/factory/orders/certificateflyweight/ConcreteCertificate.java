package pl.lab.factory.orders.certificateflyweight;

import pl.lab.factory.SystemLogger;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Flyweight 1 (ConcreteFlyweight)
// Niemutowalna implementacja Certificate. Intrinsic state: name — przechowywany w obiekcie, współdzielony między zamówieniami.
// Extrinsic state: orderId — przekazywany z zewnątrz przy wywołaniu display(), nie przechowywany w obiekcie — flyweight
// sam nie wie, w którym zamówieniu jest używany.
final class ConcreteCertificate implements Certificate {

    // intrinsic state — niezmienny, współdzielony
    private final String name;

    ConcreteCertificate(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    // extrinsic state (orderId) przekazywany przez klienta przy każdym wywołaniu
    @Override
    public void display(String orderId) {
        SystemLogger.INSTANCE.logInfo("Certificate",
                "Order " + orderId + " has certificate: " + name);
    }

    @Override
    public String toString() {
        return name;
    }
}
// Koniec, Tydzień 4
// Wzorzec Flyweight 1