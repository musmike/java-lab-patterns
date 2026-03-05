package pl.lab.factory.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OrderSpecification implements Cloneable {
    private double voltageTolerance;   // dopuszczalne odchylenie napięcia [%]
    private double thicknessTolerance; // dopuszczalne odchylenie grubości [mm]
    private List<String> certificates; // np. "IPC-A-610", "RoHS", "ISO9001"
    private String qualityStandard;    // np. "Class 2", "Class 3"

    public OrderSpecification(double voltageTolerance, double thicknessTolerance,
                              List<String> certificates, String qualityStandard) {
        this.voltageTolerance = voltageTolerance;
        this.thicknessTolerance = thicknessTolerance;
        this.certificates = new ArrayList<>(certificates);
        this.qualityStandard = qualityStandard;
    }

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Prototype 2
    // Prototype – umożliwia szybkie tworzenie kopii istniejącej specyfikacji zamówienia.
    // Metoda clone() tworzy nowy obiekt OrderSpecification z tymi samymi parametrami i kopią listy certyfikatów.

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

    public List<String> getCertificates() { return Collections.unmodifiableList(certificates); }

    public String getQualityStandard() { return qualityStandard; }

    public void setVoltageTolerance(double voltageTolerance) { this.voltageTolerance = voltageTolerance; }

    public void setThicknessTolerance(double thicknessTolerance) { this.thicknessTolerance = thicknessTolerance; }

    public void setQualityStandard(String qualityStandard) { this.qualityStandard = qualityStandard; }

    public void addCertificate(String certificate) { this.certificates.add(certificate); }

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
        private List<String> certificates = new ArrayList<>();
        private String qualityStandard;

        public Builder voltageTolerance(double voltageTolerance){
            this.voltageTolerance = voltageTolerance;
            return this;
        }

        public Builder thicknessTolerance(double thicknessTolerance){
            this.thicknessTolerance = thicknessTolerance;
            return this;
        }

        public Builder addCertificate(String certificate){
            this.certificates.add(certificate);
            return this;
        }

        public Builder qualityStandard(String qualityStandard){
            this.qualityStandard = qualityStandard;
            return this;
        }

        public OrderSpecification build(){
            return new OrderSpecification(
                    voltageTolerance,
                    thicknessTolerance,
                    certificates,
                    qualityStandard
            );
        }
    }

    // Koniec, Tydzień 2
    // Wzorzec Builder 3
}