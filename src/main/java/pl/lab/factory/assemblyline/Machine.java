package pl.lab.factory.assemblyline;

import pl.lab.factory.assemblyline.machinestates.IdleState;
import pl.lab.factory.assemblyline.machinestates.MachineState;
import pl.lab.factory.assemblyline.machinestates.OffState;
import pl.lab.factory.assemblyline.machinestates.RunningState;
import pl.lab.factory.pcbboards.BaseBoard;

public abstract class Machine {
    private MachineState state;


    
    public Machine() {
        state = new OffState();
    }

    public void perform(BaseBoard board){
        if(state instanceof OffState){
            try {
                state.call(this);
                state = new IdleState();
            } catch (InterruptedException ex) {
                System.out.println("Machine state: error!");
            }
        }
        if(state instanceof IdleState){
            try {
                state.call(this);
                state = new RunningState();
            } catch (InterruptedException ex) {
                System.out.println("Machine state: error!");
            }
        }
    };
}
