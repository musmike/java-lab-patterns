package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.pcbboards.BaseBoard;

import java.util.List;
// tydzien 7 - stosowanie zasad SRP i OCP
// zasada SRP - klasa posiadająca jedną odpowiedzialność - wykoanie podanych jej kroków wytworzenia płytki
// zasada OCP - klasa otwarta na rozszerzenie - dodanie nowych kroków składania płytki - ale zamknieta na modyfikacje - edycja żadnej z metod nie jest wymagana
// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Facade 2 - fasada upraszczajaca wykonywanie kolejnych kroków składania płytke
public class AssemblyStepManufacturingFacade {
    private List<AssemblyStep> steps;

    public AssemblyStepManufacturingFacade(List<AssemblyStep> steps) {
        this.steps = steps;
    }

    public void manufacture(BaseBoard baseBoard) throws InterruptedException {
        for (AssemblyStep step : steps) {
            step.perform(baseBoard);
        }
    }
}
//facade 2
//koniec tydzien 4
//koniec tydzien 7