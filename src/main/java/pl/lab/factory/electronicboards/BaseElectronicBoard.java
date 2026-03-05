package pl.lab.factory.electronicboards;

import pl.lab.factory.pcbboards.BaseBoard;

public class BaseElectronicBoard implements Cloneable {
    private BaseBoard baseBoard;
    private String firmware;
    private ElectronicType boardType;
    @Override
    public BaseElectronicBoard clone() {
        try {
            BaseElectronicBoard clone = (BaseElectronicBoard) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void setBaseBoard(BaseBoard baseBoard) {
        this.baseBoard = baseBoard;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public void setBoardType(ElectronicType boardType) {
        this.boardType = boardType;
    }
}
