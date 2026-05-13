package pl.lab.factory.electronicboards;

import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;
import pl.lab.factory.electronicboards.firmware.memento.FirmwareMemento;
import pl.lab.factory.manufacturing.ManufacturableProduct;
import pl.lab.factory.pcbboards.BaseBoard;

public class BaseElectronicBoard implements Cloneable, ManufacturableProduct {
    private BaseBoard baseBoard;
    private String firmware;
    private boolean programmed = false;
    private ElectronicType boardType;

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Prototype 1
    // Klonowanie BaseElectronicBoard — kopia głęboka zapewnia, że sklonowana
    // płytka elektroniczna posiada własną, niezależną instancję BaseBoard

    @Override
    public BaseElectronicBoard clone() {
        try {
            BaseElectronicBoard clone = (BaseElectronicBoard) super.clone();
            if (this.baseBoard != null) {
                clone.baseBoard = this.baseBoard.deepCopy();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    // Koniec, Tydzień 2
    // Wzorzec Prototype 1

    public void setBaseBoard(BaseBoard baseBoard) {
        this.baseBoard = baseBoard;
    }

    public void setFirmware(FirmwareAdapter adapter) {
        this.firmware = adapter.getFirmwareString();
    }

    public void setBoardType(ElectronicType boardType) {
        this.boardType = boardType;
    }

    public BaseBoard getBaseBoard() { return baseBoard; }

    public String getFirmware() { return firmware; }

    public ElectronicType getBoardType() { return boardType; }
    
    public void programBoard(){
        if(firmware != null && !firmware.isEmpty())
        {programmed = true;}
    }
    
    public boolean isProgrammed() { return firmware != null && !firmware.isEmpty() && programmed; }
    
    public FirmwareMemento saveFirmware(){
        return new FirmwareMemento(firmware, programmed);
    }
    
    public void restoreFirmware(FirmwareMemento memento){
        this.firmware = memento.getFirmware();
        this.programmed = memento.isProgrammed();
        System.out.println("Returned to old firmware state.");
    }
}