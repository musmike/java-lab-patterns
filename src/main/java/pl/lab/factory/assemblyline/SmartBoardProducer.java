package pl.lab.factory.assemblyline;

import pl.lab.factory.assemblyline.assemblingsteps.*;
import pl.lab.factory.assemblyline.assemblingstrategies.*;
import pl.lab.factory.assemblyline.assemblingstrategies.safetyproxies.PrintingProxy;
import pl.lab.factory.assemblyline.coordination.FactoryMachineMediator;
import pl.lab.factory.assemblyline.coordination.MachineMediator;
import pl.lab.factory.assemblyline.smartinterpreter.BaseProducerExpression;
import pl.lab.factory.assemblyline.smartinterpreter.MaterialExpression;
import pl.lab.factory.assemblyline.smartinterpreter.OrExpression;
import pl.lab.factory.assemblyline.smartinterpreter.SolderExpression;
import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.BoardMaterials;
import pl.lab.factory.pcbboards.SolderMaterials;

import java.util.List;

public class SmartBoardProducer implements BoardProducer {

    @Override
    public void produce(BaseBoard baseBoard) throws InterruptedException {
        MachineMediator machineMediator = new FactoryMachineMediator();

        AssemblyStep printStep = createPrintStep(machineMediator);
        AssemblyStep mountStep = createMountStep(machineMediator, baseBoard);
        AssemblyStep solderStep = createSolderStep(machineMediator, baseBoard);

        AssemblyStepManufacturingFacade assembler = new AssemblyStepManufacturingFacade(
                List.of(mountStep, printStep, solderStep)
        );
        
        System.out.println("[BOARD PRODUCTION] Manufacturing board using smart producer...");
        assembler.manufacture(baseBoard);
    }

    private AssemblyStep createPrintStep(MachineMediator machineMediator) {
        return new PrintStep(new PrintingProxy(new LaserPrint(machineMediator)));
    }

    private AssemblyStep createMountStep(MachineMediator machineMediator, BaseBoard baseBoard) {
        BaseProducerExpression copperOrAluminiumExpression = new OrExpression(
                new MaterialExpression(BoardMaterials.COPPER),
                new MaterialExpression(BoardMaterials.ALUMINIUM)
        );
        
        MountingStrategy mountingStrategy = copperOrAluminiumExpression.interpret(baseBoard) 
                ? new ManualMount(machineMediator) 
                : new AutoMount(machineMediator);
                
        return new MountStep(mountingStrategy);
    }

    private AssemblyStep createSolderStep(MachineMediator machineMediator, BaseBoard baseBoard) {
        BaseProducerExpression goldSolderExpression = new SolderExpression(SolderMaterials.WITH_GOLD);
        
        SolderingStrategy solderStrat = goldSolderExpression.interpret(baseBoard) 
                ? new ReflowSolder(machineMediator) 
                : new WaveSolder(machineMediator);
                
        return new SolderStep(solderStrat);
    }
}
