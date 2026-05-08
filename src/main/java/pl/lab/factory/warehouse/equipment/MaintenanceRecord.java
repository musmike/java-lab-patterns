package pl.lab.factory.warehouse.equipment;

import java.time.LocalDate;

public record MaintenanceRecord(LocalDate lastServiceDate, int operatingHours) {
}
