package pl.lab.factory.orders.certificateflyweight;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Flyweight 1 (Flyweight – interfejs)
// Wspólny kontrakt dla wszystkich obiektów certyfikatów. getName() zwraca stan wewnętrzny (intrinsic) — niezmienny,
// współdzielony. display() przyjmuje stan zewnętrzny (extrinsic) — orderId zależy od kontekstu użycia i nie jest
// przechowywany w flyweighcie.
public interface Certificate {
    String getName();
    void display(String orderId); // orderId = extrinsic state
}
// Koniec, Tydzień 4
// Wzorzec Flyweight 1