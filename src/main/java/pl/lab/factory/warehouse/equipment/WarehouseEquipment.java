package pl.lab.factory.warehouse.equipment;

import java.time.LocalDate;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Iterator 2 (Element kolekcji)
public class WarehouseEquipment {
    private final String  id;
    private final String name;
    private LocalDate lastServiceDate;
    private int operatingHours;

    public WarehouseEquipment(String id, String name, LocalDate lastServiceDate, int operatingHours){
        this.id = id;
        this.name = name;
        this.lastServiceDate = lastServiceDate;
        this.operatingHours = operatingHours;
    }

    public WarehouseEquipment(String id, String name){
        this(id, name, LocalDate.now(), 0);
    }

    public boolean needsMaintenance(){
        return operatingHours >= 500 || lastServiceDate.isBefore(LocalDate.now().minusYears(1));
    }

    @Override
    public String toString(){
        return name+" "+id+" | Last Service: "+lastServiceDate+" | Hours: "+operatingHours;
    }

}
// Koniec, Tydzień 5
// Wzorzec Iterator 2