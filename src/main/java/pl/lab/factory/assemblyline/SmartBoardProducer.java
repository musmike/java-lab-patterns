package pl.lab.factory.assemblyline;

import pl.lab.factory.assemblyline.assemblingsteps.AssemblyStep;
import pl.lab.factory.assemblyline.assemblingsteps.MountStep;
import pl.lab.factory.assemblyline.assemblingsteps.PrintStep;
import pl.lab.factory.assemblyline.assemblingsteps.SolderStep;
import pl.lab.factory.assemblyline.assemblingstrategiers.*;
import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.BoardMaterials;
import pl.lab.factory.pcbboards.SolderMaterials;

public class SmartBoardProducer implements BoardProducer  {
    @Override
    public void produce(BaseBoard board) throws InterruptedException {
        AssemblyStep printing = new PrintStep(new LaserPrint());
        AssemblyStep mounting = new MountStep(
                board.getBoardMaterial() == BoardMaterials.COPPER || board.getBoardMaterial() == BoardMaterials.ALUMINIUM
                        ? new ManualMount() : new AutoMount());
        AssemblyStep soldering = new SolderStep(board.getSolderType() == SolderMaterials.WITH_GOLD
                ? new ReflowSolder() : new WaveSolder());

        printing.perform(board);
        mounting.perform(board);
        soldering.perform(board);
    }
}
