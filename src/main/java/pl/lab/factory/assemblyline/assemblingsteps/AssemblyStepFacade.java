package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.pcbboards.BaseBoard;

import java.util.ArrayList;
import java.util.List;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Facade 2 - fasada upraszczajaca wykonywanie kolejnych kroków składania płytke
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
        short cnt = 0;
        for(AssemblyStep step : steps){

            cnt++;
            System.out.println("Manufacturing step " + cnt + "/" + steps.size() + " done...");
            step.perform(board);
        }
        System.out.println("Streamlined manufacturing completed!");
        if(board.isReady()){
            System.out.println("Board ready!");
        } else System.out.println("Board unready!");
    }
}
//facade 2
//koniec tydzien 4