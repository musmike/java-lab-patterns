package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.assemblyline.assemblingstrategies.SolderingStrategy;

//Tydzien 8 - Podstawienie Liskov
//klasa którą można podstawić pod klasę dziedziczoną w dowolnym miejscu
public class SolderStep extends AssemblyStep{
    public SolderStep(SolderingStrategy strategy) {
        super(strategy);
    }

    @Override
    protected String getStepDescription() {
        return "Soldering components...";
    }
}
//LSP 3
//koniec tydzien 8