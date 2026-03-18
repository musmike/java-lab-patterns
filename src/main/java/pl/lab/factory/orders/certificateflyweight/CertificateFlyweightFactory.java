package pl.lab.factory.orders.certificateflyweight;

import java.util.HashMap;
import java.util.Map;


// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Flyweight 1 (Flyweight Factory)
// Zarządza pulą współdzielonych obiektów Certificate.
// Gwarantuje, że dla tej samej nazwy certyfikatu istnieje dokładnie jedna instancja.
// Klienci zawsze przechodzą przez getCertificate() — nigdy nie tworzą ConcreteCertificate wprost.
public class CertificateFlyweightFactory {

    private static final Map<String, Certificate> pool = new HashMap<>();

    private CertificateFlyweightFactory() {}

    public static Certificate getCertificate(String name) {
        if (!pool.containsKey(name)) {
            pool.put(name, new ConcreteCertificate(name));
        }
        return pool.get(name);
    }

    public static int poolSize() {
        return pool.size();
    }
}
// Koniec, Tydzień 4
// Wzorzec Flyweight 1