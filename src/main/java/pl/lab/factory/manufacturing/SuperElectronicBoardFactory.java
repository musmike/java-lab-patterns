package pl.lab.factory.manufacturing;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.pcbboards.QXCBoard;

public class SuperElectronicBoardFactory {
    public static BaseElectronicBoard createBoard(ElectronicType type){
        BaseElectronicBoard electronicBoard = new BaseElectronicBoard();
        electronicBoard.setBoardType(type);
        electronicBoard.setBaseBoard(new QXCBoard());

        return electronicBoard;
    }
}