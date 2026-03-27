package pl.lab.factory.assemblyline;

import pl.lab.factory.assemblyline.assemblingsteps.*;
import pl.lab.factory.assemblyline.assemblingstrategiers.*;
import pl.lab.factory.assemblyline.assemblingstrategiers.safetyproxies.PrintingProxy;
import pl.lab.factory.assemblyline.assemblingstrategiers.safetyproxies.SolderingProxy;
import pl.lab.factory.assemblyline.coordination.FactoryMachineMediator;
import pl.lab.factory.assemblyline.coordination.MachineMediator;
import pl.lab.factory.assemblyline.smartinterpreter.BaseProducerExpression;
import pl.lab.factory.assemblyline.smartinterpreter.MaterialExpression;
import pl.lab.factory.assemblyline.smartinterpreter.OrExpression;
import pl.lab.factory.assemblyline.smartinterpreter.SolderExpression;
import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.BoardMaterials;
import pl.lab.factory.pcbboards.SolderMaterials;

public class SmartBoardProducer implements BoardProducer  {
    @Override
    public void produce(BaseBoard board) throws InterruptedException {
        MachineMediator mediator = new FactoryMachineMediator();

        PrintStep printing = new PrintStep(new PrintingProxy(new LaserPrint(mediator)));

        BaseProducerExpression copper_aluminium = new OrExpression(
                new MaterialExpression(BoardMaterials.COPPER),
                new MaterialExpression(BoardMaterials.ALUMINIUM));
        MountingStrategy mountStrat = copper_aluminium.interpret(board)
                ? new ManualMount(mediator) : new AutoMount(mediator);
        MountStep mounting = new MountStep(mountStrat);

        BaseProducerExpression gold_solder = new SolderExpression(SolderMaterials.WITH_GOLD);
        SolderingStrategy solderStrat = gold_solder.interpret(board)
                ? new ReflowSolder(mediator) : new WaveSolder(mediator);
        SolderStep soldering = new SolderStep(solderStrat);

        AssemblyStepFacade assembler = new AssemblyStepFacade(mounting, printing, soldering);
        System.out.println("[BOARD PRODUCTION] Manufacturing board using smart producer...");
        assembler.manufacture(board);
    }
}
