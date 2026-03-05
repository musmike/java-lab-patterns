package pl.lab.factory.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
// Wzorzec Prototype 2
//
//
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

    public double getVoltageTolerance() {
        return voltageTolerance;
    }

    public double getThicknessTolerance() {
        return thicknessTolerance;
    }

    public List<String> getCertificates() {
        return Collections.unmodifiableList(certificates);
    }

    public String getQualityStandard() {
        return qualityStandard;
    }

    public void setVoltageTolerance(double voltageTolerance) {
        this.voltageTolerance = voltageTolerance;
    }

    public void setThicknessTolerance(double thicknessTolerance) {
        this.thicknessTolerance = thicknessTolerance;
    }

    public void setQualityStandard(String qualityStandard) {
        this.qualityStandard = qualityStandard;
    }

    public void addCertificate(String certificate) {
        this.certificates.add(certificate);
    }

    @Override
    public String toString() {
        return "OrderSpecification{" +
                "voltageTolerance=" + voltageTolerance + "%, " +
                "thicknessTolerance=" + thicknessTolerance + "mm, " +
                "certificates=" + certificates + ", " +
                "qualityStandard='" + qualityStandard + "'}";
    }
}
// Koniec, Tydzień 2
// Wzorzec Prototype 2
