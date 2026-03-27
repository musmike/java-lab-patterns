package pl.lab.factory.assemblyline;

import pl.lab.factory.pcbboards.BaseBoard;

public class Mounter implements Machine{
    private static Mounter instance;

    private Mounter(){}

    public static Mounter getInstance(){
        if (instance == null) {
            instance = new Mounter();
        }
        return instance;
    }

    private void mountBoard(BaseBoard board){
        board.mountComponents();
    }

    @Override
    public void perform(BaseBoard board) {

        System.out.println("[MOUNTER] Mounting board!");
        mountBoard(board);
    }
}
