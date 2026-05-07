package pl.lab.factory.warehouse.equipment;

import java.util.List;
import java.util.NoSuchElementException;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Iterator 2 (Konkretny iterator)
public class EquipmentServiceIterator implements EquipmentIterator{
    private final List<WarehouseEquipment> equipmentList;
    private int position = 0;

    public EquipmentServiceIterator(List<WarehouseEquipment> equipmentList){
        this.equipmentList = equipmentList;
    }

    @Override
    public boolean hasNext(){
        while(position<equipmentList.size()){
            if(equipmentList.get(position).needsMaintenance()){
                return true;
            }
            position++;
        }
        return false;
    }

    @Override
    public WarehouseEquipment next(){
        if (!hasNext()) {
            throw new NoSuchElementException("No more equipment requires maintenance.");
        }
        return equipmentList.get(position++);
    }
}
// Koniec, Tydzień 5
// Wzorzec Iterator 2