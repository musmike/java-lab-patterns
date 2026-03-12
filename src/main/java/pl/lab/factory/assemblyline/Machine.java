package pl.lab.factory.assemblyline;

import pl.lab.factory.pcbboards.BaseBoard;

public abstract class Machine {
    public abstract void perform(BaseBoard board);
}
