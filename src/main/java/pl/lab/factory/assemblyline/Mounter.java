package pl.lab.factory.assemblyline;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.pcbboards.BaseBoard;

public class Mounter extends Machine{
    private static Mounter instance;

    private Mounter(){}

    public static Mounter getInstance(){
        if (instance == null) {
            instance = new Mounter();
        }
        return instance;
    }

    private void mountBoard(BaseBoard baseBoard){
        baseBoard.mountComponents();
    }

    @Override
    public void perform(BaseBoard baseBoard) {
        super.perform(baseBoard);
        SystemLogger.INSTANCE.log("[MOUNTER]", "Mounting board!");
        mountBoard(baseBoard);
    }
}
