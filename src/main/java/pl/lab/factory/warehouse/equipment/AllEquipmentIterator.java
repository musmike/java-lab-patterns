package pl.lab.factory.warehouse.equipment;

import java.util.List;

public class AllEquipmentIterator implements EquipmentIterator{
    private final List<WarehouseEquipment> equipmentList;
    private int position = 0;

    public AllEquipmentIterator(List<WarehouseEquipment> equipmentList){
        this.equipmentList = equipmentList;
    }

    @Override
    public boolean hasNext(){
        return position < equipmentList.size();
    }

    @Override
    public WarehouseEquipment next(){
        return equipmentList.get(position++);
    }
}

