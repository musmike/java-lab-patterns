package pl.lab.factory.orders.observer;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Observer 1 (Konkretny obserwator)
// Aktualizuje centralny system ERP w fabryce, gwarantując spójność danych.
public class ErpSystemUpdater implements OrderObserver {
    @Override
    public void update(ProductionOrder order) {
        SystemLogger.INSTANCE.logInfo("ERP SYNC",
                "Synchronizing order " + order.getId()
                        + " with external ERP system. Current status: " + order.getStatus());
    }
}
// Koniec, Tydzień 6
// Wzorzec Observer 1