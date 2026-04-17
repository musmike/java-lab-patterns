package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.pcbboards.BaseBoard;

import java.util.ArrayList;
import java.util.List;
// tydzien 7 - stosowanie zasad SRP i OCP
// zasada SRP - klasa posiadająca jedną odpowiedzialność - wykoanie podanych jej kroków wytworzenia płytki
// zasada OCP - klasa otwarta na rozszerzenie - dodanie nowych kroków składania płytki - ale zamknieta na modyfikacje - edycja żadnej z metod nie jest wymagana
// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Facade 2 - fasada upraszczajaca wykonywanie kolejnych kroków składania płytke
public class AssemblyStepFacade {
    private List<AssemblyStep> steps;

    public AssemblyStepFacade(List<AssemblyStep> steps) {
        this.steps = steps;
    }

    public void manufacture(BaseBoard board) throws InterruptedException {
        short cnt = 0;
        for(AssemblyStep step : steps){

            cnt++;
            step.perform(board);
        }
    }
}
//facade 2
//koniec tydzien 4
//koniec tydzien 7