package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.pcbboards.BaseBoard;

public interface AssemblingStrategy {
    void execute(BaseBoard board) throws InterruptedException;
}
