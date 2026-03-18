package pl.lab.factory.assemblyline;

import pl.lab.factory.assemblyline.assemblingsteps.*;
import pl.lab.factory.assemblyline.assemblingstrategiers.*;
import pl.lab.factory.assemblyline.assemblingstrategiers.safetyproxies.PrintingProxy;
import pl.lab.factory.assemblyline.assemblingstrategiers.safetyproxies.SolderingProxy;
import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.BoardMaterials;
import pl.lab.factory.pcbboards.SolderMaterials;

public class SmartBoardProducer implements BoardProducer  {
    @Override
    public void produce(BaseBoard board) throws InterruptedException {
        PrintStep printing = new PrintStep(new PrintingProxy(new LaserPrint()));
        MountStep mounting = new MountStep(
                board.getBoardMaterial() == BoardMaterials.COPPER || board.getBoardMaterial() == BoardMaterials.ALUMINIUM
                        ? new ManualMount() : new AutoMount());
        SolderStep soldering = new SolderStep(new SolderingProxy(
                board.getSolderType() == SolderMaterials.WITH_GOLD
                        ? new ReflowSolder() : new WaveSolder()
        ));

        AssemblyStepFacade assembler = new AssemblyStepFacade(mounting, printing, soldering);
        assembler.manufacture(board);
    }
}
