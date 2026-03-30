package pl.lab.factory.warehouse;

import pl.lab.factory.warehouse.equipment.AllEquipmentIterator;
import pl.lab.factory.warehouse.equipment.EquipmentIterator;
import pl.lab.factory.warehouse.equipment.EquipmentServiceIterator;
import pl.lab.factory.warehouse.equipment.WarehouseEquipment;
import pl.lab.factory.warehouse.equipment.observer.MaintenanceObserver;

import java.util.ArrayList;
import java.util.List;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Iterator 2 (Konkretny agregat)
public class WarehouseAssetsManager {
    private final List<WarehouseEquipment> equipmentFleet = new ArrayList<>();

    public void subscribeAll(MaintenanceObserver observer){
        for(WarehouseEquipment equipment : equipmentFleet){
            equipment.addObserver(observer);
        }
    }

    public void addEquipment(WarehouseEquipment equipment){
        equipmentFleet.add(equipment);
    }

    public EquipmentIterator getEquipmentIterator(){return new AllEquipmentIterator(equipmentFleet);
    }
    public EquipmentIterator getMaintenanceIterator(){
        return new EquipmentServiceIterator(equipmentFleet);
    }
}
// Koniec, Tydzień 5
// Wzorzec Iterator 2