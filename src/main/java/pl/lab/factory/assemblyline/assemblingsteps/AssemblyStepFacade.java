package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.pcbboards.BaseBoard;

import java.util.ArrayList;
import java.util.List;

public class AssemblyStepFacade {
    private List<AssemblyStep> steps;

    public AssemblyStepFacade(List<AssemblyStep> steps) {
        this.steps = steps;
    }

    public AssemblyStepFacade(MountStep mounting, PrintStep printing, SolderStep soldering){
        steps = new ArrayList<>();
        if(mounting!=null) steps.add(mounting);
        if(printing!=null) steps.add(printing);
        if(soldering!= null ) steps.add(soldering);
    }

    public void manufacture(BaseBoard board) throws InterruptedException {
        short cnt = 1;
        for(AssemblyStep step : steps){
            step.perform(board);
            System.out.println("Manufacturing step " + cnt + "/" + steps.size() + " done...");
        }
        System.out.println("Streamlined manufacturing completed!");
    }
}
