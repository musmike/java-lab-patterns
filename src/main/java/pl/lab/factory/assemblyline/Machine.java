package pl.lab.factory.assemblyline;

import pl.lab.factory.pcbboards.BaseBoard;

public interface Machine {
    public abstract void perform(BaseBoard board);
}
