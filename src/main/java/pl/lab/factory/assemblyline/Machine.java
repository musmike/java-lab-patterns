package pl.lab.factory.assemblyline;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.assemblyline.machinestates.IdleState;
import pl.lab.factory.assemblyline.machinestates.MachineState;
import pl.lab.factory.assemblyline.machinestates.OffState;
import pl.lab.factory.assemblyline.machinestates.RunningState;
import pl.lab.factory.pcbboards.BaseBoard;

public abstract class Machine {
    private static final String LOG_MACHINE_ERROR = "Machine state: error!";

    private MachineState state;

    public Machine() {
        state = new OffState();
    }

    public void perform(BaseBoard baseBoard) {
        if (state instanceof OffState) {
            transitionFromOff();
        }
        if (state instanceof IdleState) {
            transitionFromIdle();
        }
    }

    private void transitionFromOff() {
        try {
            state.call(this);
            state = new IdleState();
        } catch (InterruptedException ex) {
            SystemLogger.INSTANCE.logError("Machine", LOG_MACHINE_ERROR);
        }
    }

    private void transitionFromIdle() {
        try {
            state.call(this);
            state = new RunningState();
        } catch (InterruptedException ex) {
            SystemLogger.INSTANCE.logError("Machine", LOG_MACHINE_ERROR);
        }
    }
}
