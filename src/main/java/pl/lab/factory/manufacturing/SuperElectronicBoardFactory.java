package pl.lab.factory.manufacturing;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.pcbboards.QXCBoard;
import pl.lab.factory.pcbboards.StandardBoard;

public class SuperElectronicBoardFactory {
    public static BaseElectronicBoard createBoard(ElectronicType type){
        BaseElectronicBoard board = new BaseElectronicBoard();
        board.setBoardType(type);
        board.setBaseBoard(new QXCBoard());

        return board;
    }
}