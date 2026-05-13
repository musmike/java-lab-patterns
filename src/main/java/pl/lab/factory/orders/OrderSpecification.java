package pl.lab.factory.orders;

import pl.lab.factory.orders.certificateflyweight.Certificate;
import pl.lab.factory.orders.certificateflyweight.CertificateFlyweightFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OrderSpecification implements Cloneable {
    private double voltageTolerance;   // dopuszczalne odchylenie napięcia [%]
    private double thicknessTolerance; // dopuszczalne odchylenie grubości [mm]

    // Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
    // Wzorzec Flyweight 1
    // Zamiast List<String> przechowujemy List<Certificate> — referencje do
    // współdzielonych obiektów z puli CertificatePool. Klonowanie listy
    // kopiuje jedynie referencje (flyweighty są niemutowalne, więc to bezpieczne).
    private List<Certificate> certificates; // np. "IPC-A-610", "RoHS", "ISO9001"
    // Koniec, Tydzień 4
    // Wzorzec Flyweight 1

    private String qualityStandard;    // np. "Class 2", "Class 3"

    private OrderSpecification(Builder builder) {
        this.voltageTolerance = builder.voltageTolerance;
        this.thicknessTolerance = builder.thicknessTolerance;
        this.certificates = new ArrayList<>(builder.certificates);
        this.qualityStandard = builder.qualityStandard;
    }

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Prototype 2
    // Klonowanie listy certyfikatów kopiuje tylko referencje do flyweightów —
    // nie powstają nowe obiekty Certificate
    @Override
    public OrderSpecification clone() {
        try {
            OrderSpecification clone = (OrderSpecification) super.clone();
            clone.certificates = new ArrayList<>(this.certificates);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    // Koniec, Tydzień 2
    // Wzorzec Prototype 2

    public double getVoltageTolerance() { return voltageTolerance; }

    public double getThicknessTolerance() { return thicknessTolerance; }

    public List<Certificate> getCertificates() { return Collections.unmodifiableList(certificates); }

    public String getQualityStandard() { return qualityStandard; }

    public void setVoltageTolerance(double voltageTolerance) { this.voltageTolerance = voltageTolerance; }

    public void setThicknessTolerance(double thicknessTolerance) { this.thicknessTolerance = thicknessTolerance; }

    public void setQualityStandard(String qualityStandard) { this.qualityStandard = qualityStandard; }

    // Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
    // Wzorzec Flyweight 1
    // Współdzieli certyfikaty z puli zamiast tworzyć nowe.
    // Kontekst (orderId) przekazywany z zewnątrz przy użyciu obiektu.
    public void addCertificate(String name) {
        this.certificates.add(CertificateFlyweightFactory.getCertificate(name));
    }

    public void displayCertificates(String orderId) {
        certificates.forEach(certificate -> certificate.display(orderId));
    }
    // Koniec, Tydzień 4
    // Wzorzec Flyweight 1

    @Override
    public String toString() {
        return "OrderSpecification{" +
                "voltageTolerance=" + voltageTolerance + "%, " +
                "thicknessTolerance=" + thicknessTolerance + "mm, " +
                "certificates=" + certificates + ", " +
                "qualityStandard='" + qualityStandard + "'}";
    }

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Builder 3

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private double voltageTolerance;
        private double thicknessTolerance;

        // Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
        // Wzorzec Flyweight 1
        private List<Certificate> certificates = new ArrayList<>();
        // Koniec, Tydzień 4
        // Wzorzec Flyweight 1

        private String qualityStandard;

        public Builder voltageTolerance(double voltageTolerance){
            this.voltageTolerance = voltageTolerance;
            return this;
        }

        public Builder thicknessTolerance(double thicknessTolerance){
            this.thicknessTolerance = thicknessTolerance;
            return this;
        }

        // Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
        // Wzorzec Flyweight 1
        // Builder pobiera Certificate z puli zamiast dodawać surowego Stringa.
        public Builder addCertificate(String name){
            this.certificates.add(CertificateFlyweightFactory.getCertificate(name));
            return this;
        }
        // Koniec, Tydzień 4
        // Wzorzec Flyweight 1

        public Builder qualityStandard(String qualityStandard){
            this.qualityStandard = qualityStandard;
            return this;
        }

        public OrderSpecification build(){
            return new OrderSpecification(this);
        }
    }

    // Koniec, Tydzień 2
    // Wzorzec Builder 3
}